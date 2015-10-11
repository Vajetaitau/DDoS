(function () {

    var cv = angular.module('cv');

    cv.controller('DDOSController', ['$scope', '$state', 'ddosService', 'sourceList',
        function ($scope, $state, ddosService, sourceList) {
            $scope.getLine = getLine;
            $scope.uploadFile = uploadFile;

            var ipAddresses = [];
            var ipAdressCounts = [];
            $scope.threshold = 2000;
			$scope.amountToReturn = 50;
			$scope.sourceList = sourceList;
            $scope.first_labels = ipAddresses;
            $scope.first_data = [ipAdressCounts];

			getGroupedSourceIps();
			recalculateSourceList();

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
				ddosService.getGroupedSourceIps($scope.threshold, $scope.amountToReturn, 'desc').then(function (response) {
					$scope.sourceList = response.data.list;
				})
			}

            $scope.first_series = ['Series A', 'Series B'];

            $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
              $scope.series = ['Series A', 'Series B'];
              $scope.data = [
                [65, 59, 80, 81, 56, 55, 40],
                [28, 48, 40, 19, 86, 27, 90]
              ];
              $scope.onClick = function (points, evt) {
                console.log(points, evt);
              };

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

			$scope.$watch('sourceList', function(oldValue, newValue) {
				if (!_.isEqual(oldValue, newValue)) {
					getGroupedSourceIps();
				}
			});

        }]);

}());