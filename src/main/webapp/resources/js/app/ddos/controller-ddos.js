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
			$scope.seriesAIpSource = ddosService.defaults.seriesAIpSource;
			$scope.seriesAIpDestination = ddosService.defaults.seriesAIpDestination;
			$scope.seriesBIpSource = ddosService.defaults.seriesBIpSource;
			$scope.seriesBIpDestination = ddosService.defaults.seriesBIpDestination;
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
                    console.log('finished');
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
					var date = new Date(pc.time).customFormat('#YYYY#/#MM#/#DD# #hh#:#mm#:#ss#');
					if (time.length == 0 || time[time.length - 1] != date) {
						time.push(date);
					}
					if (time.length > seriesA.length) {
						seriesA.push(0);
						seriesB.push(0);
					}
					if (pc.file === '0') {
						seriesA[time.length - 1] = pc.count;
					} else if (pc.file === '1') {
						seriesB[time.length - 1] = pc.count;
					}
				}
				$scope.second_labels = time;
				$scope.second_data = [seriesA, seriesB];
			}

			function recalculatePacketCount() {
				var timeFrom = Math.round(new Date($scope.timeFrom).getTime() / 1000);
				var timeTo = Math.round(new Date($scope.timeTo).getTime() / 1000);
				var seriesA = {source: $scope.seriesAIpSource, destination: $scope.seriesAIpDestination, returnSource: true};
				ddosService.getPacketCount(timeFrom, timeTo, $scope.increment, [seriesA]).then(function (response) {
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

			$scope.$watch('seriesAIpSource', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('seriesAIpDestination', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('seriesBIpSource', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
				}
			});

			$scope.$watch('seriesBIpDestination', function(oldValue, newValue) {
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