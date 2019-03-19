/**
 * Created by PpTMUnited on 1/13/2017.
 */
(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ScheduleController', ScheduleController);

    ScheduleController.$inject = ['$scope', '$state', '$q', 'DTColumnBuilder', 'DTOptionsBuilder',
        'GUIUtils', '$filter', 'CoreService', 'Chart', '$sessionStorage', '$compile', 'GroupSession', 'moment', 'GroupSessionDetails',
    'GROUP_SESSION_DETAIL_PROGRESS', 'DateUtils'];

    function ScheduleController($scope, $state, $q, DTColumnBuilder, DTOptionsBuilder,
                                GUIUtils, $filter, CoreService, Chart, $sessionStorage, $compile, GroupSession, moment,
                                GroupSessionDetails, GROUP_SESSION_DETAIL_PROGRESS, DateUtils) {
        var vm = this;
        //Functions
        vm.search = search;

        //Variable
        if($sessionStorage.groupSessionCalendar){
            vm.calendarDate = new Date($sessionStorage.groupSessionCalendar);
        }else{
            vm.calendarDate = new Date();
            $sessionStorage.groupSessionCalendar = vm.calendarDate;
        }

        vm.today = new Date();
        vm.dtInstanceGroupSessions = {};
        vm.dtInstance = {};

        vm.changeCalendar = function () {
            vm.dtInstanceGroupSessions.reloadData();
            vm.dtInstance.reloadData();
            $sessionStorage.groupSessionCalendar =  vm.calendarDate;
        }


        vm.rmConfig = {
            mondayStart: false,
            initState: "month",
            maxState: "decade",
            minState: "month",
            decadeSize: 12,
            monthSize: 42,
            min: new Date("2016-01-01"),
            max: null,
            format: "yyyy-MM-dd"
        };


        vm.dtOptionsGroupSessions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                vm.facility = CoreService.getCurrentFacility();

                GroupSession.groupsessionsbyday({id: vm.facility.id, date: DateUtils.convertLocalDateToServer(vm.calendarDate)}, function (result) {
                    preparingData(result);
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.dtInstanceGroupSessions, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumnsGroupSessions = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '40px').notSortable()
                .renderWith(function (data, type, full) {
                    return actionsHtml(data);
                })
        ];


        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();

            if (!vm.searchQuery || vm.searchQuery == '') {
                vm.facility = CoreService.getCurrentFacility();


                GroupSessionDetails.searchByDate({id: vm.facility.id, date: vm.calendarDate}, function (result) {
                    preparingData(result);
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.dtInstance, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });


        vm.dtColumns = [
            DTColumnBuilder.newColumn('groupSession.name').withTitle('Name').withOption('width', '30px'),
            DTColumnBuilder.newColumn(null).withTitle('Leader').withOption('width', '50px').renderWith(function (data, type, full) {
                return data.leaderEmployee.firstName + " " + data.leaderEmployee.lastName
            }),
            DTColumnBuilder.newColumn(null).withTitle('Times').withOption('width', '50px').renderWith(function (data, type, full) {

                var startDate = new Date(data.start);
                var endDate = new Date(data.finalized);

                return vm.completeDate(startDate.getHours()) + ':' + vm.completeDate(startDate.getMinutes()) + " - " + vm.completeDate(endDate.getHours()) + ':' + vm.completeDate(endDate.getMinutes());
            }),
            DTColumnBuilder.newColumn('progress').withTitle('Progress').withOption('width', '30px').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlStatus(data);
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '20px').notSortable()
                .renderWith(groupSessionDetailsActionsHtml)
        ];


        function actionsHtml(data, type, full, meta) {
            var myHtml =  '&nbsp;&nbsp;&nbsp;' ;

           // if(vm.dateIsToday()){
             myHtml = myHtml + '<a class="btn btn-xs btn-success"  ui-sref="group-session-details-new({gid:' + data.id + '})" title="Start Event"> <i class="fa fa-play"></i></a>' ;
            //

            return myHtml;
        }

        function groupSessionDetailsActionsHtml(data, type, full) {
            return '<a class="btn-sm btn-primary"   ui-sref="group-session-details-detail({id:' + data.id + '})"   title="View Group Session" href=""><i class="fa fa-eye"></i></a>' ;
        }



        vm.enabledEmployeeSign = function (data) {
            //If current Employee is leader of GroupSessionDetails
            var employee = CoreService.getCurrentEmployee();

            if (data.leaderEmployee.id == employee.id  && vm.isInProgress(data) )//&& vm.dateIsToday()
                return true;

            return false;
        }


        vm.enabledReviewSign = function (data) {

            if (vm.PendingReview(data) && vm.employeeIsReview(data))
                return true;

            return false;
        }

        vm.employeeIsReview = function(data){
            var employee = CoreService.getCurrentEmployee();

            if(employee){
                var authorities = employee.user.authorities;
                var roles = ["ROLE_SUPER_ADMIN", "ROLE_CASE_MANAGER", "ROLE_PROGRAM_DIRECTOR", "ROLE_PRIMARY_THERAPIST",
                    "ROLE_OTHER_THERAPIST", "ROLE_CLINICAL_DIRECTOR", "ROLE_PHYSICIAN_ASSISTANCE", "ROLE_MD"];

                for(var i=0; i<authorities.length; i++){
                    for(var j=0; j<roles.length; j++){
                        if(authorities[i].name ==roles[j] && data.leaderEmployee.id != employee.id)
                            return true;
                    }
                }
            }


            return false;
        }


        function search() {
            vm.dtOptionsGroupSessions = vm.loadGroupSessionDay();
            vm.dtOptionsGroupSessions.reloadData();
        }


        function preparingData(data) {
            var startTime = null;
            var endTime = null;
            for (var i = 0; i < data.length; i++) {
                startTime = moment(data[i].startTime).format('hh:mm:ss a');
                endTime = moment(data[i].endTime).format('hh:mm:ss a');
                data[i].rangeTime = startTime + ' to ' + endTime;
            }

            return data;
        }

        vm.loadAll = function (date) {
            var defer = $q.defer();
            GroupSession.groupsessionsbyday({id: vm.facility.id, date: DateUtils.convertLocalDateToServer(vm.calendarDate)}, function (result) {
                vm.dtOptionsGroupSessions = preparingData(result);
                defer.resolve(result);
                return defer.promise;
            });
        }


        vm.dateIsToday = function () {
            var now = new Date();

            var calendarYear = vm.calendarDate.getFullYear();
            var calendarMonth = vm.calendarDate.getMonth();
            var calendarDay = vm.calendarDate.getDate();

            var nowYear = now.getFullYear();
            var nowMonth = now.getMonth();
            var nowDay = now.getDate();

            return calendarYear == nowYear && calendarMonth == nowMonth && calendarDay == nowDay;

        }

        vm.completeDate = function (dig) {

            if (dig < 10) {
                return '0' + dig;
            }

            return dig;
        }


        vm.isInProgress = function(data)
        {
            if (data.progress == GROUP_SESSION_DETAIL_PROGRESS.IN_PROCESS) {
                return true;
            }
            return false;

        }

        vm.PendingReview = function(data)
        {
            if (data.progress == GROUP_SESSION_DETAIL_PROGRESS.PENDING_REVIEW) {
                return true;
            }
            return false;
        }

    }
})();

