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


				timeFrom:'1999-03-08 08:00:00',
				timeTo: '1999-03-08 08:25:00',
				increment: 1,
				seriesAIpSource: '*',
				seriesAIpDestination: '172.16.112.100',



//				timeFrom:'1999-03-08 08:45:00',
//				timeTo: '1999-03-08 09:25:00',
//				increment: 1,
//				seriesAIpSource: '*',
//				seriesAIpDestination: '172.16.113.50',



//				timeFrom:'1999-03-08 09:30:00',
//				timeTo: '1999-03-08 09:50:00',
//				increment: 1,
//				seriesAIpSource: '*',
//				seriesAIpDestination: '172.16.114.50',



//				timeFrom:'1999-03-08 12:00:00',
//				timeTo: '1999-03-08 12:25:00',
//				increment: 1,
//				seriesAIpSource: '*',
//				seriesAIpDestination: '172.16.112.50',



//				timeFrom:'1999-03-08 15:50:00',
//				timeTo: '1999-03-08 16:10:00',
//				increment: 1,
//				seriesAIpSource: '*',
//				seriesAIpDestination: '172.16.112.50',



//				timeFrom:'1999-03-08 17:20:00',
//				timeTo: '1999-03-08 17:40:00',
//				increment: 1,
//				seriesAIpSource: '*',
//				seriesAIpDestination: '172.16.114.50',



//				timeFrom:'1999-03-08 19:00:00',
//				timeTo: '1999-03-08 19:25:00',
//				increment: 1,
//				seriesAIpSource: '*',
//				seriesAIpDestination: '172.16.112.50',



				seriesBIpSource: '19*',
				seriesBIpDestination: '172.*'
			}

        };

    }]);

}());