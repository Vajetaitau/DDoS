(function () {

    var cv = angular.module('cv');

    cv.controller('DDOSController', ['$scope', '$state',
        function ($scope, $state) {

            $scope.getLine = getLine;

            function getLine() {
                alert('xx');
            }

        }]);

}());