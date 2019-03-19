(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceDialogController', InsuranceDialogController);

    InsuranceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Insurance', 'InsuranceCarrier', 'InsuranceType', 'InsuranceLevel', 'InsuranceRelation', 'Patient', 'CountryState'];

    function InsuranceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Insurance, InsuranceCarrier, InsuranceType, InsuranceLevel, InsuranceRelation, Patient, CountryState) {
        var vm = this;

        vm.insurance = entity;
        vm.clear = clear;
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.insurancecarriers = InsuranceCarrier.query();
        vm.insurancetypes = InsuranceType.query();
        vm.insurancelevels = InsuranceLevel.query();
        vm.insurancerelations = InsuranceRelation.query();
        vm.patients = Patient.query();
        vm.countrystates = CountryState.query();
        vm.datePickerOpenStatus = {};

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.insurance.id !== null) {
                Insurance.update(vm.insurance, onSaveSuccess, onSaveError);
            } else {
                Insurance.save(vm.insurance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:insuranceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dob = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
