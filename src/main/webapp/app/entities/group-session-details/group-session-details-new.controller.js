(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsNewController', GroupSessionDetailsNewController);

    GroupSessionDetailsNewController.$inject = ['$timeout', '$scope', 'entity', 'GroupSessionDetails',
        '$state', 'Chart', 'GroupSession', 'CoreService', '$sessionStorage', 'GROUP_SESSION_DETAIL_PROGRESS', '$uibModalInstance', '$stateParams'];

    function GroupSessionDetailsNewController ($timeout, $scope, entity, GroupSessionDetails, $state, Chart,  GroupSession, CoreService,
                                               $sessionStorage,  GROUP_SESSION_DETAIL_PROGRESS, $uibModalInstance, $stateParams) {
        var vm = this;
        this.$sessionStorage = $sessionStorage;


        vm.groupSessionDetails = entity;

        GroupSession.get({id: $stateParams.gid}, function(data){
            vm.groupSessionDetails.groupSession = data;

            vm.groupSessionDetails.start = vm.groupSessionDetails.groupSession.startTime;
            vm.groupSessionDetails.finalized = vm.groupSessionDetails.groupSession.endTime;
            console.log(vm.groupSessionDetails);
        });


        vm.form = {};
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.currentPatients = Chart.currentPatient({id: CoreService.getCurrentFacility().id});
        vm.groupDetailsChart = {};
        vm.groupDetailsChartList = [];
        $sessionStorage.groupDetailsChartList = [];


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.save =function () {

            var groupSessionDetailsSave = angular.copy(vm.groupSessionDetails);

            groupSessionDetailsSave.topic = groupSessionDetailsSave.groupSession.name;
            groupSessionDetailsSave.progress = GROUP_SESSION_DETAIL_PROGRESS.IN_PROCESS;

            var startTimeHour = groupSessionDetailsSave.start.getHours();
            var startTimeMinutes = groupSessionDetailsSave.start.getMinutes();

            groupSessionDetailsSave.start = new Date($sessionStorage.groupSessionCalendar);
            groupSessionDetailsSave.start.setHours(startTimeHour);
            groupSessionDetailsSave.start.setMinutes(startTimeMinutes);
            groupSessionDetailsSave.start.setMilliseconds(0);

            if(groupSessionDetailsSave.finalized){
                var finalizedTimeHour = groupSessionDetailsSave.finalized.getHours();
                var finalizedTimeMinutes = groupSessionDetailsSave.finalized.getMinutes();

                groupSessionDetailsSave.finalized = new Date($sessionStorage.groupSessionCalendar);
                groupSessionDetailsSave.finalized.setHours(finalizedTimeHour);
                groupSessionDetailsSave.finalized.setMinutes(finalizedTimeMinutes);
                groupSessionDetailsSave.finalized.setMilliseconds(0);
            }

            vm.isSaving = true;

            if (vm.groupSessionDetails.id !== null) {
                GroupSessionDetails.update(groupSessionDetailsSave, onSaveSuccess, onSaveError);
            } else {
                GroupSessionDetails.save(groupSessionDetailsSave, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $scope.$emit('bluebookApp:groupSessionDetailsUpdate', result);
            $state.go('today-group-session', null, { reload: true });
            $uibModalInstance.close(result);
        }

        function onSaveError (error) {
            vm.isSaving = false;
            console.log(error);
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


    }
})();
