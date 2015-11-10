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

            , parseAttackFile: function() {
            	return $http.post('action/ddos/parseAttackFile');
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
				attackList: [{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 08:39:50","timeTo":"1999-04-05 08:40:04"},{"source":"*","destination":"192.168.1.1","increment":"1","timeFrom":"1999-04-05 08:43:15","timeTo":"1999-04-05 08:43:20"},{"source":"*","destination":"192.168.1.1","increment":"1","timeFrom":"1999-04-05 08:45:11","timeTo":"1999-04-05 08:45:16"},{"source":"*","destination":"192.168.1.1","increment":"1","timeFrom":"1999-04-05 08:47:07","timeTo":"1999-04-05 08:47:12"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 08:48:31","timeTo":"1999-04-05 08:48:40"},{"source":"*","destination":"202.77.162.213","increment":"1","timeFrom":"1999-04-05 08:49:01","timeTo":"1999-04-05 08:49:06"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 08:59:14","timeTo":"1999-04-05 08:59:59"},{"source":"*","destination":"207.75.239.115","increment":"1","timeFrom":"1999-04-05 08:59:43","timeTo":"1999-04-05 08:59:48"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:32:58","timeTo":"1999-04-05 09:35:02"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:43:09","timeTo":"1999-04-05 09:43:21"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:43:22","timeTo":"1999-04-05 09:43:27"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:43:29","timeTo":"1999-04-05 09:43:36"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:43:33","timeTo":"1999-04-05 09:43:40"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:43:41","timeTo":"1999-04-05 09:43:46"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:43:48","timeTo":"1999-04-05 09:43:54"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:43:55","timeTo":"1999-04-05 09:44:00"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:02","timeTo":"1999-04-05 09:44:10"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:07","timeTo":"1999-04-05 09:44:15"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:16","timeTo":"1999-04-05 09:44:21"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:23","timeTo":"1999-04-05 09:44:31"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:32","timeTo":"1999-04-05 09:44:37"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:39","timeTo":"1999-04-05 09:44:45"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:42","timeTo":"1999-04-05 09:44:51"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:52","timeTo":"1999-04-05 09:44:57"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:44:59","timeTo":"1999-04-05 09:45:06"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:45:03","timeTo":"1999-04-05 09:45:09"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:45:10","timeTo":"1999-04-05 09:45:15"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:45:17","timeTo":"1999-04-05 09:45:30"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:45:27","timeTo":"1999-04-05 09:45:41"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:45:42","timeTo":"1999-04-05 09:45:47"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:45:50","timeTo":"1999-04-05 09:46:04"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:46:01","timeTo":"1999-04-05 09:46:16"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:46:13","timeTo":"1999-04-05 09:46:23"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:46:24","timeTo":"1999-04-05 09:46:29"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:46:31","timeTo":"1999-04-05 09:46:36"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:46:33","timeTo":"1999-04-05 09:46:38"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:46:35","timeTo":"1999-04-05 09:46:50"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 09:46:51","timeTo":"1999-04-05 09:46:56"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 10:29:20","timeTo":"1999-04-05 10:47:01"},{"source":"*","destination":"202.77.162.213","increment":"1","timeFrom":"1999-04-05 10:29:26","timeTo":"1999-04-05 10:29:52"},{"source":"*","destination":"172.16.118.80","increment":"1","timeFrom":"1999-04-05 10:58:12","timeTo":"1999-04-05 11:00:02"},{"source":"*","destination":"172.16.118.80","increment":"1","timeFrom":"1999-04-05 10:59:59","timeTo":"1999-04-05 11:01:36"},{"source":"*","destination":"192.5.41.239","increment":"1","timeFrom":"1999-04-05 11:00:01","timeTo":"1999-04-05 11:00:06"},{"source":"*","destination":"172.16.112.100","increment":"1","timeFrom":"1999-04-05 11:45:25","timeTo":"1999-04-05 12:02:02"},{"source":"*","destination":"172.16.113.50","increment":"1","timeFrom":"1999-04-05 12:03:12","timeTo":"1999-04-05 12:14:31"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 12:11:16","timeTo":"1999-04-05 12:23:48"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 01:18:10","timeTo":"1999-04-05 01:18:15"},{"source":"*","destination":"172.16.112.100","increment":"1","timeFrom":"1999-04-05 01:30:12","timeTo":"1999-04-05 01:30:33"},{"source":"*","destination":"172.16.112.100","increment":"1","timeFrom":"1999-04-05 01:33:50","timeTo":"1999-04-05 01:44:53"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 02:05:41","timeTo":"1999-04-05 02:15:49"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:05:45","timeTo":"1999-04-05 02:05:50"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:11:46","timeTo":"1999-04-05 02:11:51"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:13:00","timeTo":"1999-04-05 02:13:05"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:13:14","timeTo":"1999-04-05 02:13:19"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:13:54","timeTo":"1999-04-05 02:13:59"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:14:03","timeTo":"1999-04-05 02:14:08"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:14:50","timeTo":"1999-04-05 02:14:55"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:14:53","timeTo":"1999-04-05 02:14:58"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:15:04","timeTo":"1999-04-05 02:15:09"},{"source":"*","destination":"152.169.215.104","increment":"1","timeFrom":"1999-04-05 02:15:17","timeTo":"1999-04-05 02:15:22"},{"source":"*","destination":"206.48.44.50","increment":"1","timeFrom":"1999-04-05 02:16:49","timeTo":"1999-04-05 02:16:54"},{"source":"*","destination":"172.16.113.50","increment":"1","timeFrom":"1999-04-05 02:22:28","timeTo":"1999-04-05 02:22:33"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 02:46:17","timeTo":"1999-04-05 02:46:31"},{"source":"*","destination":"172.16.113.1","increment":"1","timeFrom":"1999-04-05 03:00:14","timeTo":"1999-04-05 03:00:19"},{"source":"*","destination":"172.16.113.3","increment":"1","timeFrom":"1999-04-05 03:04:04","timeTo":"1999-04-05 03:04:09"},{"source":"*","destination":"172.16.113.5","increment":"1","timeFrom":"1999-04-05 03:07:54","timeTo":"1999-04-05 03:07:59"},{"source":"*","destination":"172.16.113.4","increment":"1","timeFrom":"1999-04-05 03:11:44","timeTo":"1999-04-05 03:11:49"},{"source":"*","destination":"172.16.113.50","increment":"1","timeFrom":"1999-04-05 03:15:34","timeTo":"1999-04-05 03:15:39"},{"source":"*","destination":"204.233.47.21","increment":"1","timeFrom":"1999-04-05 03:15:34","timeTo":"1999-04-05 03:15:39"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 04:32:15","timeTo":"1999-04-05 04:32:29"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 04:32:26","timeTo":"1999-04-05 04:33:01"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 04:32:58","timeTo":"1999-04-05 04:33:24"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 04:33:21","timeTo":"1999-04-05 04:42:05"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 04:42:02","timeTo":"1999-04-05 04:46:11"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 04:46:08","timeTo":"1999-04-05 04:48:54"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 05:19:08","timeTo":"1999-04-05 05:34:13"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 06:04:02","timeTo":"1999-04-05 06:10:57"},{"source":"*","destination":"172.16.112.100","increment":"1","timeFrom":"1999-04-05 06:36:09","timeTo":"1999-04-05 06:51:20"},{"source":"*","destination":"172.16.112.20","increment":"1","timeFrom":"1999-04-05 06:57:19","timeTo":"1999-04-05 06:57:24"},{"source":"*","destination":"172.16.115.234","increment":"1","timeFrom":"1999-04-05 07:47:59","timeTo":"1999-04-05 08:04:44"},{"source":"*","destination":"172.16.113.*ud","increment":"1","timeFrom":"1999-04-05 08:00:25","timeTo":"1999-04-05 08:15:29"},{"source":"*","destination":"172.16.112.*ud","increment":"1","timeFrom":"1999-04-05 08:00:25","timeTo":"1999-04-05 08:15:29"},{"source":"*","destination":"172.16.112.50","increment":"1","timeFrom":"1999-04-05 08:17:10","timeTo":"1999-04-05 08:20:17"},{"source":"*","destination":"172.16.114.50","increment":"1","timeFrom":"1999-04-05 08:46:11","timeTo":"1999-04-05 08:47:44"},{"source":"*","destination":"172.16.118.70","increment":"1","timeFrom":"1999-04-05 08:46:33","timeTo":"1999-04-05 08:47:24"}],

				timeFrom:'1999-04-05 08:39:50',
				timeTo: '1999-04-05 08:40:04',
				increment: 1,
				seriesAIpSource: '*',
				seriesAIpDestination: '172.16.112.50',


//				timeFrom:'1999-03-08 08:00:00',
//				timeTo: '1999-03-08 08:25:00',
//				increment: 1,
//				seriesAIpSource: '*',
//				seriesAIpDestination: '172.16.112.100',



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