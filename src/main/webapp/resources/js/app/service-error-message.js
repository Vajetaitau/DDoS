(function () {

    var cv = angular.module('cv');

    cv.service('errorMessageService', [function () {

        return {
            wrongCredentials: 'WRONG CREDENTIALS'
        };

    }]);

}());