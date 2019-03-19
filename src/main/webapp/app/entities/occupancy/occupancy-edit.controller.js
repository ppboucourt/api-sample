(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OccupancyEditController', OccupancyEditController);

    OccupancyEditController.$inject = ['$scope', '$state', 'Bed', 'BedSearch', '$q',
        'GUIUtils', '$filter', 'CoreService', 'Chart', 'entity', '$uibModalInstance'];

    function OccupancyEditController ($scope, $state, Bed, BedSearch, $q,
                            GUIUtils, $filter, CoreService, Chart, entity, $uibModalInstance) {
        var vm = this;

        //Functions
        vm.clear = clear;
        vm.save = save;

        //Variable
        vm.bed = entity;
        vm.beds = Bed.getBedsByIds({ids: CoreService.getUnassignedBeds()});
        vm.selectedToEdit = prepareList();

        function prepareList() {
            var copy = {};
            angular.copy(vm.bed, copy);
            vm.beds.push(copy);
            return copy;
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            var bedToChart = {};
            var oldBed = {};
            if(vm.bed){
                angular.copy(vm.bed, oldBed);
                if(vm.selectedToEdit){
                    vm.bed.name = vm.selectedToEdit.name;
                    vm.bed.notes = vm.selectedToEdit.notes;
                    vm.bed.id = vm.selectedToEdit.id;
                    vm.bed.roomId = vm.selectedToEdit.roomId;
                }else{

                }
                bedToChart = prepareBedToChart(vm.bed);
                Bed.update(vm.bed, onSaveSuccess, onSaveError);
                vm.bed.activeChart.bed = bedToChart;
                Chart.update(vm.bed.activeChart, onSaveChartSuccess, onSaveError);
                oldBed.actualChart = null;
                Bed.update(oldBed, onSaveSuccess, onSaveError);

            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:bedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function onSaveChartSuccess() {
            vm.isSaving = false;
        }

        function prepareBedToChart(bed) {
            var bedToChart = {};
            angular.copy(bed, bedToChart);
            delete bedToChart.activeChart;
            delete bedToChart.actualChart;

            return bedToChart;
        }

    }
})();
