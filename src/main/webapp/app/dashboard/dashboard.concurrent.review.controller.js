(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DashboardConcurrentReviewController', DashboardConcurrentReviewController);

    DashboardConcurrentReviewController.$inject = ['$scope', 'CoreService', '$q',
        '$rootScope', 'Corporation', 'Dashboard', '$compile', 'DateUtils', '$timeout'];

    function DashboardConcurrentReviewController($scope, CoreService, $q,
                                                 $rootScope, Corporation, Dashboard, $compile, DateUtils, $timeout) {

        var vm = this;
        vm.concurrentReviewsDate = {};
        vm.calendarDate = new Date();

        vm.rmConfig = {
            mondayStart: false,
            initState: "month",
            maxState: "decade",
            minState: "month",
            decadeSize: 12,
            monthSize: 42,
            min: null,
            max: null,
            format: "yyyy-MM-dd"
        };


        vm.changeCalendar = function () {
            loadAll();
        }

        //Variable
        vm.facility = CoreService.getCurrentFacility();
        vm.dtInstanceConcurrentReview = {};

        $rootScope.$on('bluebookApp:setCurrentFacility', function (event, result) {
            vm.facility = result;
        });

        $timeout(function () {
            loadAll();
        }, 250);

        function loadAll() {
                    Corporation.get({id: 1}, function (result) {
                        CoreService.setCorporation(result);

                        Dashboard.dConcurrentsReviewByDateVO({
                            fId: vm.facility.id,
                            date: DateUtils.convertLocalDateToServer(vm.calendarDate)
                        }, function (result) {
                            vm.concurrentReviewsDateVO = result;
                        })

                    });
        }

    }


})();
