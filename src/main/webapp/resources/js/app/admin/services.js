(function () {

    var cv = angular.module('cv');

    cv.service('adminUserService',['$rootScope', function($rootScope) {

        return {

            createUser: function(user) {
//                return commonService.oneParamPost('action/user', user);
            }

        };

    }]);

}());