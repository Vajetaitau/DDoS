(function () {

    var cv = angular.module('cv');

    cv.controller('DDOSController', ['$scope', '$state', 'ddosService', 'sourceList', 'packetCounts',
        function ($scope, $state, ddosService, sourceList, packetCounts) {
            $scope.getLine = getLine;
            $scope.uploadFile = uploadFile;

            var ipAddresses = [];
            var ipAdressCounts = [];
            $scope.threshold = 2000;
			$scope.amountToReturn = 50;
			$scope.source = true;
			$scope.sourceList = sourceList;
            $scope.first_labels = ipAddresses;
            $scope.first_data = [ipAdressCounts];

			$scope.packetCounts = packetCounts;
			$scope.timeFrom = ddosService.defaults.timeFrom;
			$scope.timeTo = ddosService.defaults.timeTo;
			$scope.increment = ddosService.defaults.increment;
			$scope.seriesAIp = ddosService.defaults.seriesAIp;
			$scope.seriesASource = ddosService.defaults.seriesASource;
			$scope.seriesBIp = ddosService.defaults.seriesBIp;
			$scope.seriesBSource = ddosService.defaults.seriesBSource;
            $scope.second_labels = [];
            $scope.second_data = [[]];
            $scope.series = ['Series A', 'Series B'];

			getGroupedSourceIps();
			getPacketCounts();

            function getLine() {
                ddosService.getLine().then(function (response) {
                    console.log(response.data.line);
                });
            }

            function uploadFile() {
                ddosService.uploadFile().then(function (response) {
                    alert('x');
                });
            }

            function getGroupedSourceIps() {
            	ipAddresses.length = 0;
            	ipAdressCounts.length = 0;
				for (var i in $scope.sourceList) {
					var source = $scope.sourceList[i];
					ipAddresses.push(source.ip);
					ipAdressCounts.push(source.count);
				}
            }

            function recalculateSourceList() {
				ddosService.getGroupedSourceIps($scope.threshold, $scope.amountToReturn, $scope.source).then(function (response) {
					$scope.sourceList = response.data.list;
				});
			}

			function getPacketCounts() {
				$scope.second_labels = [];
				$scope.second_data = [[]];
				var time = [];
				var seriesA = [];
				var seriesB = [];
				for (var i in $scope.packetCounts) {
					var pc = $scope.packetCounts[i];
					if (time.length == 0 || time[time.length - 1] != pc.time) {
						time.push(pc.time);
					}
					if (time.length > seriesA.length) {
						seriesA.push(0);
						seriesB.push(0);
					}
					if (pc.series == 'a') {
						seriesA[time.length - 1] = pc.count;
					} else if (pc.series == 'b') {
						seriesB[time.length - 1] = pc.count;
					}
				}
				$scope.second_labels = time;
				$scope.second_data = [seriesA, seriesB];
			}

			function recalculatePacketCount() {
				var seriesA = {ip: $scope.seriesAIp, isSource: $scope.seriesASource};
                var seriesB = {ip: $scope.seriesBIp, isSource: $scope.seriesBSource};
				ddosService.getPacketCount($scope.timeFrom, $scope.timeTo, $scope.increment, [seriesA, seriesB]).then(function (response) {
					$scope.packetCounts = response.data.list;
				});
			}

			$scope.$watch('threshold', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculateSourceList();
				}
			});

			$scope.$watch('amountToReturn', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculateSourceList();
				}
			});

			$scope.$watch('source', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculateSourceList();
				}
			});

			$scope.$watch('sourceList', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					getGroupedSourceIps();
				}
			});

			$scope.$watch('timeFrom', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('timeTo', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('increment', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('seriesAIp', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('seriesASource', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('seriesBIp', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('seriesBSource', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('packetCounts', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					getPacketCounts();
				}
			});
        }]);

}());