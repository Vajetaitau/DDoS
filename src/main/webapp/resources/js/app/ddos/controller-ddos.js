(function () {

    var cv = angular.module('cv');

    cv.controller('DDOSController', ['$scope', '$state', 'ddosService', 'sourceList', 'packetCounts',
        function ($scope, $state, ddosService, sourceList, packetCounts) {
            $scope.getLine = getLine;
            $scope.uploadFile = uploadFile;
            $scope.parseAttackFile = parseAttackFile;

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
			var time = [];
			var seriesA = [];
			var seriesB = [];
            $scope.second_labels = time;
            $scope.second_data = [seriesA, seriesB];
            $scope.series = ['Series A', 'Series B'];

            $scope.ddosAttackList = ddosService.defaults.attackList;
            $scope.attackSelected = function(attack) {
            	$scope.timeFrom = attack.timeFrom;
            	$scope.timeTo = attack.timeTo;
				$scope.increment = attack.increment;
				$scope.seriesAIpSource = attack.source;
				$scope.seriesAIpDestination = attack.destination;
            }

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

            function parseAttackFile() {
            	ddosService.parseAttackFile().then(function (response) {
            		console.log('parsed!');
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
				time.length = 0;
				seriesA.length = 0;
				seriesB.length = 0;
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
				_.defer(function(){$scope.$apply();});
				console.log($scope.second_labels);
			}

			function recalculatePacketCount() {
				var timeFrom = new Date($scope.timeFrom);
				var timeTo = new Date($scope.timeTo);
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

			$scope.$watch('packetCounts', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					getPacketCounts();
				}
			});
        }]);

}());