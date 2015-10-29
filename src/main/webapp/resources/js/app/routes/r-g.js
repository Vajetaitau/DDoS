(function () {//guest user routes

    var cv = angular.module('cv');

    cv.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/");

        $stateProvider
            .state('guest', {
                'url': '/',
                'templateUrl': 'html/public/main.html',
                'controller': 'MainController'
            })
        ;

        $stateProvider
			.state('ddos', {
				'url': '/ddos',
				'templateUrl': 'html/public/ddos.html',
				'controller': 'DDOSController',
				'resolve': {
					sourceList: ['ddosService', function(ddosService) {
						var defaults = ddosService.defaults;
						return ddosService.getGroupedSourceIps(defaults.threshold, defaults.amountToReturn, defaults.source).then(function (response) {
							var sourceList = response.data.list;
							return sourceList;
						})
					}],
					packetCounts: ['ddosService', function(ddosService) {
						var defaults = ddosService.defaults;
						var timeFrom = new Date(defaults.timeFrom);//Math.round(new Date(defaults.timeFrom).getTime());
						console.log(timeFrom.toUTCString());
                        var timeTo = Math.round(new Date(defaults.timeTo).getTime());
						var seriesA = {source: defaults.seriesAIpSource, destination: defaults.seriesAIpDestination, returnSource: true};

						return ddosService.getPacketCount(timeFrom, timeTo, defaults.increment, [seriesA]).then(function (response) {
							var packetCountsResponse = response.data.list;
							return packetCountsResponse;
						});
					}]
				}
			})
		;

    }]);

}());