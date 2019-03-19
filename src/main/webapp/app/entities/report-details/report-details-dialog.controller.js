(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ReportDetailsDialogController', ReportDetailsDialogController);

    ReportDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ReportDetails', 'Reports', 'Tables', 'Fields'];

    function ReportDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ReportDetails, Reports, Tables, Fields) {
        var vm = this;

        vm.reportDetails = entity;
        vm.clear = clear;
        vm.save = save;
        vm.reports = Reports.query();
        vm.tables = Tables.query();
        vm.fields = Fields.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reportDetails.id !== null) {
                ReportDetails.update(vm.reportDetails, onSaveSuccess, onSaveError);
            } else {
                ReportDetails.save(vm.reportDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:reportDetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
