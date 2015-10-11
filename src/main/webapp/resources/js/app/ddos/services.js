(function () {

    var cv = angular.module('cv');

    cv.service('ddosService',['$rootScope', 'commonService', function($rootScope, commonService) {

        return {

            getLine: function() {
                return commonService.oneParamGet('action/ddos/line');
            }

        };

    }]);

}());