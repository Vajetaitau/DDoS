(function () {

    var cv = angular.module('cv');

    cv.controller('Admin-Main', ['$scope', '$state',
        function ($scope, $state) {

            $scope.openFiles = openFiles;
            $scope.openUsers = openUsers;

            function openFiles() {
                openPage('admin.files');
            }

            function openUsers() {
                openPage('admin.users');
            }

            function openPage(state) {
                $state.go(state);
            }

        }]);

}());