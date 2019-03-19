(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsurancePatientController', InsurancePatientController);

    InsurancePatientController.$inject = ['$filter', 'chart', 'InsuranceType', 'InsuranceLevel', 'InsuranceRelation', 'InsuranceCarrier',
        '$scope', 'CountryState'];

    function InsurancePatientController ($filter, chart, InsuranceType, InsuranceLevel, InsuranceRelation, InsuranceCarrier,
                                         $scope, CountryState ) {
        var vm = this;
        //Functions

        vm.addSecondaryInsurance = addSecondaryInsurance;
        vm.deleteSecondaryInsurance = deleteSecondaryInsurance;
        vm.deletePrimaryInsurance = deletePrimaryInsurance;
        vm.swapInsurances = swapInsurances;
        vm.openInsuranceCalendar = openInsuranceCalendar;
        vm.getInsuranceCarrierLoaded = getInsuranceCarrierLoaded;

        //Variables
        vm.chart = chart;
        vm.title = 'Insurances';
        vm.insurances = [];
        vm.carriers = [];
        vm.dtInstance = {};
        vm.insuranceType = InsuranceType.query();
        vm.insuranceLevels = InsuranceLevel.query();
        vm.insuranceRelations = InsuranceRelation.query();
        vm.insuranceLoaded = InsuranceCarrier.query();
        vm.states = CountryState.query();

        vm.datePickerOpenStatus = {};
        vm.form = {};
        vm.updating = false;
        vm.editInsurance = false;
        vm.datePickerOpenStatus.insuranceDob = false;
        vm.datePickerOpenStatus.insuranceDob = [false, false];

        loadAll();
        function loadAll() {
            if(vm.chart.typePaymentMethods.category == 'Insurance'){
                if(!vm.chart.insurances.length)
                    addFirstInsurance();
            }
        }

        //Update Btns
        vm.updating = false;
        vm.cancel = cancel;
        vm.updateForm = updateForm;

        function cancel(value) {
            if(vm.chart.insurances.length > 1){
                if(!vm.chart.insurances[1].id){
                    vm.chart.insurances.splice(1, 1);
                }
            }
            vm.updating = false;
            vm.editInsurance = false;
        }

        function updateForm() {
            vm.updating = true;
            vm.editInsurance = true;
        }

        function getInsuranceCarrierLoaded(query) {
            if (query && query.length > 2) {
                vm.carriers = $filter('filter')(vm.insuranceLoaded, query, undefined);
            }
            if(vm.carriers.length == 0 || query.length == 0){
                vm.carriers = vm.insuranceLoaded;
            }
        }

        function addSecondaryInsurance() {
            vm.chart.insurances[1] = {};
            vm.chart.insurances[1].insuranceOrder = 2;
        }

        function addFirstInsurance() {
            vm.chart.insurances[0] = {};
            vm.chart.insurances[0].insuranceOrder = 1;
        }

        function deletePrimaryInsurance() {
            vm.swapInsurances();
            vm.deleteSecondaryInsurance();
        }

        function deleteSecondaryInsurance() {
            vm.chart.insurances.splice(1, 1);
        }

        function swapInsurances() {
            var tempInsurance = {};

            angular.copy(vm.chart.insurances[0], tempInsurance);
            angular.copy(vm.chart.insurances[1], vm.chart.insurances[0]);
            angular.copy(tempInsurance, vm.chart.insurances[1]);

            vm.chart.insurances[0].insuranceOrder = 1;
            vm.chart.insurances[1].insuranceOrder = 2;
        }

        function openInsuranceCalendar(index) {
            vm.datePickerOpenStatus.insuranceDob[index] = true;
        }

        function save () {
            vm.isSaving = true;
            if(vm.chart.insurances.length > 1){
                if (vm.chart.insurances[0].id !== null) {
                    Insurance.update(vm.chart.insurances[0], onSaveSuccess, onSaveError);
                } else {
                    Insurance.save(vm.chart.insurances[0], onSaveSuccess, onSaveError);
                }
            }else{
                for (var i = 0; i < vm.chart.insurances.length; i++){
                    if (vm.chart.insurances[i].id !== null) {
                        Insurance.update(vm.chart.insurances[i], onSaveSuccess, onSaveError);
                    } else {
                        Insurance.save(vm.chart.insurances[i], onSaveSuccess, onSaveError);
                    }
                }
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:insuranceUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
