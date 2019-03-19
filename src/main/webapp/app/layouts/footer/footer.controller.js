/**
 * Created by hermeslm on 10/2/16.
 */

(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FooterController', FooterController);

    FooterController.$inject = ['AdminLTEService', '$timeout', '$rootScope', 'Dashboard'];

    function FooterController(AdminLTEService, $timeout, $rootScope, Dashboard) {
        var vm = this;

        $rootScope.$on('$viewContentLoading',
            function (event, viewConfig) {
                // Access to all the view config properties.
                // and one special property 'targetView'
                // viewConfig.targetView
                $timeout(function () {
                    $timeout(function () {
                        AdminLTEService.layoutFix();
                    }, 0);
                }, 0);
            });


        Dashboard.currentVersion(function(data){
            if(data.value > 0){
                vm.BUILD = data.value;
                // var build = $localStorage.build?parseInt($localStorage.build): 0;
                // if(build || build < vm.BUILD){
                //
                //     $localStorage.build = vm.BUILD;
                //
                //     if( window.location.indexOf('?')> 0){
                //         window.location = window.location + "&random=" + Math.round(Math.random()*100000) ;
                //     }else{
                //         window.location = window.location + "?random=" + Math.round(Math.random()*100000) ;
                //     }
                //
                // }

            }else{
                vm.BUILD = "DEV";
            }
        }, function (error){
            vm.BUILD = "DEV";
        })

    }
})();

