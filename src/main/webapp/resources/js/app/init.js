(function () {

    var cv = angular.module('cv', [
        'ngRoute',
        'ngCookies',
        'pascalprecht.translate',
//        'http-auth-interceptor',
        'ui.router',
        'ngDialog',/* https://github.com/likeastore/ngDialog */
        'chart.js'
    ]);

    cv.config(['$httpProvider', 'ngDialogProvider',
        function ($httpProvider, ngDialogProvider) {

            ngDialogProvider.setDefaults({
                className: 'ngdialog-theme-default',
                plain: false,
                showClose: true,
                closeByDocument: true,
                closeByEscape: true,
                appendTo: false,
                cache: false,
                preCloseCallback: function () {
                    console.log('default pre-close callback');
                }
            });

            $httpProvider.interceptors.push('cv_sessionInjector');

        }]);

    cv.run(['$rootScope', '$http', '$location', '$translate', '$cookieStore', 'cv_globalService',
        function ($rootScope, $http, $location, $translate, $cookieStore,  globalService) {

            var XAuthToken = $cookieStore.get('X-Auth-Token');
            if (!_.isEmpty(XAuthToken)) {
                globalService.setToken(XAuthToken);
            }

            $rootScope.toggleLanguage = function () {
                $translate.use(($translate.use() === 'en') ? 'lt' : 'en');
            };

            Date.prototype.customFormat = function(formatString){
				var YYYY,YY,MMMM,MMM,MM,M,DDDD,DDD,DD,D,hhhh,hhh,hh,h,mm,m,ss,s,ampm,AMPM,dMod,th;
				YY = ((YYYY=this.getFullYear())+"").slice(-2);
				MM = (M=this.getMonth()+1)<10?('0'+M):M;
				MMM = (MMMM=["January","February","March","April","May","June","July","August","September","October","November","December"][M-1]).substring(0,3);
				DD = (D=this.getDate())<10?('0'+D):D;
				DDD = (DDDD=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"][this.getDay()]).substring(0,3);
				th=(D>=10&&D<=20)?'th':((dMod=D%10)==1)?'st':(dMod==2)?'nd':(dMod==3)?'rd':'th';
				formatString = formatString.replace("#YYYY#",YYYY).replace("#YY#",YY).replace("#MMMM#",MMMM).replace("#MMM#",MMM).replace("#MM#",MM).replace("#M#",M).replace("#DDDD#",DDDD).replace("#DDD#",DDD).replace("#DD#",DD).replace("#D#",D).replace("#th#",th);
				h=(hhh=this.getHours());
				if (h==0) h=24;
				if (h>12) h-=12;
				hh = h<10?('0'+h):h;
				hhhh = h<10?('0'+hhh):hhh;
				AMPM=(ampm=hhh<12?'am':'pm').toUpperCase();
				mm=(m=this.getMinutes())<10?('0'+m):m;
				ss=(s=this.getSeconds())<10?('0'+s):s;
				return formatString.replace("#hhhh#",hhhh).replace("#hhh#",hhh).replace("#hh#",hh).replace("#h#",h).replace("#mm#",mm).replace("#m#",m).replace("#ss#",ss).replace("#s#",s).replace("#ampm#",ampm).replace("#AMPM#",AMPM);
            };

        }]);

}());