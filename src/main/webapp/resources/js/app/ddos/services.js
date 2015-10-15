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

            , getGroupedSourceIps: function(threshold, limit, source) {
                return $http.get('action/ddos/groupedIps?threshold=' + threshold + '&limit=' + limit + '&order=desc' + '&source=' + source);
            }

            , getPacketCount: function(start, end, increment, list) {
				return $http.post('action/ddos/packetCount', {
					start: start,
					end: end,
					increment: increment,
					 list: list
				});
			}

			, defaults: {
				source: true,
				threshold: 2000,
				amountToReturn: 50,
				timeFrom: 0,
				timeTo: 12000,
				increment: 200,
				seriesAIp: '172.16.112.194',
				seriesASource: true,
				seriesBIp: '172.16.113.148',
				seriesBSource: true
			}

        };

    }]);

}());