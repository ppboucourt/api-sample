(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsSignController',GroupSessionDetailsSignController);

    GroupSessionDetailsSignController.$inject = ['$uibModalInstance', 'entity', 'GroupSessionDetails', 'CoreService', 'Employee', 'GROUP_SESSION_DETAIL_PROGRESS'];

    function GroupSessionDetailsSignController($uibModalInstance, entity, GroupSessionDetails, CoreService, Employee, GROUP_SESSION_DETAIL_PROGRESS) {
        var vm = this;

        vm.groupSessionDetails = entity;
        vm.clear = clear;
        vm.confirmSign = confirmSign;
        vm.employee = CoreService.getCurrentEmployee();

        vm.isReviewver = vm.groupSessionDetails.leaderEmployee.id == vm.employee.id ? false: true;

        if(!vm.isReviewver){
            vm.title = 'Sign Group Session Details';
        }else
            vm.title = 'Review and Sign Group Session Details';

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmSign (id) {

           Employee.employeeIsValidPin({id: vm.employee.id, pin:vm.employee.pin}, function(result){

               var now = new Date();
               if(result && result.value){
                   if(!vm.isReviewver){
                       vm.groupSessionDetails.progress = GROUP_SESSION_DETAIL_PROGRESS.PENDING_REVIEW;
                       vm.groupSessionDetails.groupSession.status = 'ACT';
                       vm.groupSessionDetails.leaderSignature = {date:now, created_date: now, last_modified_date: now,
                           created_by: vm.employee.user.login};

                   }else{
                       vm.groupSessionDetails.progress = GROUP_SESSION_DETAIL_PROGRESS.COMPLETED;
                       vm.groupSessionDetails.reviserSignature = {date:now, created_date: now, last_modified_date: now,
                           created_by: vm.employee.user.login};
                       vm.groupSessionDetails.reviewBy = vm.employee;
                   }


                   GroupSessionDetails.update(vm.groupSessionDetails,
                       function (data) {
                           $uibModalInstance.close(true);
                   });


               }

           }, function (error) {

           });



        }
    }
})();
