(function () {

    var cv = angular.module('cv');

    cv.controller('DDOSController', ['$scope', '$state', 'ddosService', 'sourceList', 'packetCounts', 'entropy',
        function ($scope, $state, ddosService, sourceList, packetCounts, entropy) {
            $scope.getLine = getLine;
            $scope.uploadFile = uploadFile;
            $scope.parseAttackFile = parseAttackFile;
            $scope.scanForDDoSAttacks = scanForDDoSAttacks;

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
            $scope.series = ['Inside', 'Outside'];

			$scope.entropy = entropy.listOfEntropies;
			$scope.windowWidth = ddosService.defaults.windowWidth;
            var entropyTime = [];
            var entropyData = [];
            $scope.entropy_labels = entropyTime;
            $scope.entropy_data = [entropyData];
            $scope.entropy_series = ['Entropy'];

//			$scope.entropyAverage = entropy.listOfAverages;
//            var entropyAverageTime = [];
//            var entropyAverageData = [];
//            $scope.average_labels = entropyAverageTime;
//			$scope.average_data = [entropyAverageData];
//			$scope.average_series = ['Entropy Average'];
//
//			$scope.averageDifference = entropy.listOfDifferences;
//			var averageDifferenceTime = [];
//			var averageDifferenceData = [];
//			$scope.difference_labels = averageDifferenceTime;
//			$scope.difference_data = [averageDifferenceData];
//			$scope.difference_series = ['Entropy Difference'];

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
			getEntropy();

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

            function scanForDDoSAttacks() {
            	ddosService.scanForDDoSAttacks().then(function (response) {
            		console.log('scan for DDoS attacks finished!');
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
			}

			function recalculatePacketCount() {
				var timeFrom = new Date($scope.timeFrom);
				var timeTo = new Date($scope.timeTo);
				var seriesA = {source: $scope.seriesAIpSource, destination: $scope.seriesAIpDestination, returnSource: true};
				ddosService.getPacketCount(timeFrom, timeTo, $scope.increment, [seriesA]).then(function (response) {
					$scope.packetCounts = response.data.list;
				});
			}

			function getEntropy() {
				entropyTime.length = 0;
				entropyData.length = 0;
//				entropyAverageTime.length = 0;
//				entropyAverageData.length = 0;
//				averageDifferenceTime.length = 0;
//				averageDifferenceData.length = 0;

				for (var i in $scope.entropy) {
					var e = $scope.entropy[i];

					var date = new Date(e.time).customFormat('#YYYY#/#MM#/#DD# #hh#:#mm#:#ss#');
					entropyTime.push(date);
					entropyData.push(e.entropy);
				}

//				for (var i in $scope.entropyAverage) {
//					var e = $scope.entropyAverage[i];
//
//					var date = new Date(e.time).customFormat('#YYYY#/#MM#/#DD# #hh#:#mm#:#ss#');
//					entropyAverageTime.push(date);
//					entropyAverageData.push(e.value);
//				}
//
//				for (var i in $scope.averageDifference) {
//					var e = $scope.averageDifference[i];
//
//					var date = new Date(e.time).customFormat('#YYYY#/#MM#/#DD# #hh#:#mm#:#ss#');
//					averageDifferenceTime.push(date);
//					averageDifferenceData.push(e.value);
//				}
			}

			function recalculateEntropy() {
				var timeFrom = new Date($scope.timeFrom);
				var timeTo = new Date($scope.timeTo);
				ddosService.getEntropy(timeFrom, timeTo, $scope.increment, $scope.windowWidth).then(function (response) {
					var entropyInformation = response.data.entropyInformation;
					$scope.entropy = entropyInformation.listOfEntropies;
//					$scope.entropyAverage = entropyInformation.listOfAverages;
//					$scope.averageDifference = entropyInformation.listOfDifferences;
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
					recalculateEntropy();
				}
			});

			$scope.$watch('timeTo', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
					recalculateEntropy();
				}
			});

			$scope.$watch('increment', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
					recalculateEntropy();
				}
			});

			$scope.$watch('seriesAIpSource', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
					recalculateEntropy();
				}
			});

			$scope.$watch('seriesAIpDestination', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculatePacketCount();
					recalculateEntropy();
				}
			});

			$scope.$watch('packetCounts', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					getPacketCounts();
				}
			});

			$scope.$watch('windowWidth', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					recalculateEntropy();
				}
			});

			$scope.$watch('entropy', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					getEntropy();
				}
			});

			$scope.exportFirstData = exportFirstData;
			$scope.exportSecondData = exportSecondData;
			$scope.exportEntropyData = exportEntropyData;
			$scope.exportAverageEntropyData = exportAverageEntropyData;
			$scope.exportDifferenceData = exportDifferenceData;


			function exportFirstData() {
				var name = 'exportedFirstData';
				exportData($scope.first_labels, $scope.first_data, name);
			}

			function exportSecondData() {
				var name = 'exportedSecondData';
				exportData($scope.second_labels, $scope.second_data, name);
			}

			function exportEntropyData() {
				var name = 'exportedEntropyData';
				exportData($scope.entropy_labels, $scope.entropy_data, name);
			}

			function exportAverageEntropyData() {
				var name = 'exportedAverageEntropyData';
				exportData($scope.average_labels, $scope.average_data, name);
			}

			function exportDifferenceData() {
				var name = 'exportedDifferenceData';
				exportData($scope.difference_labels, $scope.difference_data, name);
			}

			function exportData(data_x, data_y, name) {
				$scope[name] = '';
				for (var i in data_x) {
					var x_point = data_x[i];
					$scope[name] = $scope[name] + '\n' + x_point;
					for (var j in data_y) {
						var y_point = data_y[j][i];
						$scope[name] = $scope[name] + '\t' + y_point
					}
				}

			}

        }]);

}());