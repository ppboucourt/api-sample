(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsDetailController', GroupSessionDetailsDetailController);

    GroupSessionDetailsDetailController.$inject = ['$state', '$scope', '$q', '$compile', '$rootScope', 'previousState', 'entity', 'GroupSessionDetails', 'Chart', 'DTColumnBuilder', 'DTOptionsBuilder',
        'CoreService', 'GROUP_SESSION_DETAIL_PROGRESS', 'moment', 'DateUtils'];

    function GroupSessionDetailsDetailController($state, $scope, $q, $compile , $rootScope, previousState, entity, GroupSessionDetails, Chart, DTColumnBuilder, DTOptionsBuilder
    , CoreService, GROUP_SESSION_DETAIL_PROGRESS, moment, DateUtils) {


        var vm = this;
        vm.moment = moment;
        vm.CoreService = CoreService;

        vm.groupSessionDetails = entity;
      //  vm.groupSessionDetails.finalized = new Date(vm.groupSessionDetails.finalized);
       // vm.groupSessionDetails.finalized.setMilliseconds(0);

        if(vm.groupSessionDetails.start){
            vm.groupSessionDetails.start.setSeconds(0);
        }

        if(vm.groupSessionDetails.finalized){
            vm.groupSessionDetails.finalized.setSeconds(0);
        }

        vm.fullName = vm.groupSessionDetails.leaderEmployee.firstName + " " + vm.groupSessionDetails.leaderEmployee.lastName;

        if(vm.groupSessionDetails.leaderSignature && vm.groupSessionDetails.leaderSignature.date && vm.groupSessionDetails.leaderSignature.ip){
            vm.signedMessage= 'Electronically signed by ' + vm.fullName + ' on  ' + moment(vm.groupSessionDetails.leaderSignature.date).format('MMMM Do YYYY, h:mm a') + ', from ip ' + vm.groupSessionDetails.leaderSignature.ip;
        }

        if(vm.groupSessionDetails.reviserSignature && vm.groupSessionDetails.reviserSignature.createdBy && vm.groupSessionDetails.reviserSignature.date && vm.groupSessionDetails.leaderSignature.ip){
            vm.reviewMessage = 'Reviewed by ' + vm.groupSessionDetails.reviserSignature.createdBy + ' on ' + moment(vm.groupSessionDetails.reviserSignature.date).format('MMMM Do YYYY, h:mm a') + ' from ip ' + vm.groupSessionDetails.leaderSignature.ip;
        }



        if(vm.groupSessionDetails.reviewBy){
            vm.fullReviewBy = vm.groupSessionDetails.reviewBy.firstName + " " + vm.groupSessionDetails.reviewBy.lastName;
        }

        vm.previousState = previousState.name;
        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();

            if (!vm.searchQuery || vm.searchQuery == '') {
                vm.facility = CoreService.getCurrentFacility();

                GroupSessionDetails.groupSessionDetailsListChartsVO({id: vm.groupSessionDetails.id}, function(data){
                    vm.selectedChartsFromDB = data ? data : [];

                    defer.resolve(vm.selectedChartsFromDB);
                }, function(error){
                    console.log(error);
                });


            } else {
                defer.resolve($filter('filter')(vm.dtInstance, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });



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


        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Name').withOption('width', '30px').renderWith(function (data, type, full) {
                return "   " + data.firstName + " " + data.lastName;
            }),
            DTColumnBuilder.newColumn('mrNo').withTitle('MrNo').withOption('width', '30px'),
            DTColumnBuilder.newColumn('notes').withTitle('Notes').withOption('width', '30px'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '10%').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta){


            if(vm.isInProgress()){
                return '<a class="btn-sm btn-primary"  disabled="true" ui-sref="group-session-details-detail.addnote({gdid:' + data.id + '})" title="Add Note"> <i class="glyphicon glyphicon-plus-sign"></i></a>' +
                    '&nbsp;&nbsp;&nbsp;' +
                    '<a class="btn-sm btn-danger"  ui-sref="group-session-details-detail.delete({gdid:' + data.id + '})" title="Delete Current Patient"> <i class="glyphicon glyphicon-trash"></i></a>' ;
            }else{
                return '<a class="btn-sm btn-primary" style="cursor: default;pointer-events: none; color: #5a7b8c!important;  text-decoration: none;" title="Add Note Disabled">  <i class="glyphicon glyphicon-plus-sign"></i></a>' +
                    '&nbsp;&nbsp;&nbsp;' +
                    '<a class="btn-sm btn-danger" style="cursor: default;pointer-events: none; color: #5a7b8c!important;  text-decoration: none;" title="Delete Current Patient Disabled"> <i class="glyphicon glyphicon-trash"></i></a>' ;

            }


        }


        var unsubscribe = $rootScope.$on('bluebookApp:groupSessionDetailsUpdate', function(event, result) {
            vm.groupSessionDetails = result;
        });


        vm.isInProgress = function()
        {
            if (vm.groupSessionDetails.progress == GROUP_SESSION_DETAIL_PROGRESS.IN_PROCESS) {
                return true;
            }
            return false;

        }

        vm.PendingReview = function()
        {
            if (vm.groupSessionDetails.progress == GROUP_SESSION_DETAIL_PROGRESS.PENDING_REVIEW) {
                return true;
            }
            return false;

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



        vm.enabledEmployeeSign = function () {
            //If current Employee is leader of GroupSessionDetails
            var employee = CoreService.getCurrentEmployee();

            if (vm.groupSessionDetails.leaderEmployee.id == employee.id  && vm.isInProgress() )//&& vm.dateIsToday()
                return true;

            return false;
        }


        vm.enabledReviewSign = function () {

            if (vm.PendingReview() && vm.employeeIsReview())
                return true;

            return false;
        }

        vm.employeeIsReview = function(){
            var employee = CoreService.getCurrentEmployee();

            if(employee){
                var authorities = employee.user.authorities;
                var roles = ["ROLE_SUPER_ADMIN", "ROLE_CASE_MANAGER", "ROLE_PROGRAM_DIRECTOR", "ROLE_PRIMARY_THERAPIST",
                    "ROLE_OTHER_THERAPIST", "ROLE_CLINICAL_DIRECTOR", "ROLE_PHYSICIAN_ASSISTANCE", "ROLE_MD"];

                for(var i=0; i<authorities.length; i++){
                    for(var j=0; j<roles.length; j++){
                        if(authorities[i].name ==roles[j] && vm.groupSessionDetails.leaderEmployee.id != employee.id)
                            return true;
                    }
                }
            }


            return false;
        }

        vm.saveEntity = function(){
            GroupSessionDetails.update(vm.groupSessionDetails, function(data){
                $state.go('today-group-session', null, {reload: 'today-group-session'});
            }, function(error){
                console.log(error);
            });
        }

        vm.saveEntityBeforeSign = function(){

            GroupSessionDetails.update(vm.groupSessionDetails, function(data){

                $state.go('group-session-details-sign', ({id:vm.groupSessionDetails.id}), {reload: false});

            }, function(error){
                console.log(error);
            });
        }

        vm.employeeSign = function(){
            vm.saveEntityBeforeSign();
        }

        $scope.$on('$destroy', unsubscribe);

        vm.groupSessionDetails.reviserSignatureDateString = (vm.groupSessionDetails.reviserSignature && vm.groupSessionDetails.reviserSignature.date) ?(vm.CoreService.parseToDate(vm.groupSessionDetails.reviserSignature.date) + ' ' + vm.CoreService.parseToTime(vm.groupSessionDetails.reviserSignature.date)): '';

    }
})();
