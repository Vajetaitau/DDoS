(function () {

    var cv = angular.module('cv');

    cv.service('ddosService',['$rootScope', '$http', function($rootScope, $http) {

        return {

            getLine: function() {
                return $http.get('action/ddos/line');
            }

            , uploadFile: function() {
                return $http.post('action/ddos/uploadFile');
            }

            , getGroupedSourceIps: function(threshold, limit, order) {
                return $http.get('action/ddos/groupedSourceIps?threshold=' + threshold + '&limit=' + limit + '&order=' + order);
            }

        };

    }]);

}());