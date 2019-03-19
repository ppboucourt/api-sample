(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OccupancyAssignController', OccupancyAssignController);

    OccupancyAssignController.$inject = ['$scope', '$state', 'Bed', 'BedSearch', '$q',
        'GUIUtils', '$filter', 'CoreService', 'Chart', 'entity', '$uibModalInstance'];

    function OccupancyAssignController ($scope, $state, Bed, BedSearch, $q,
                            GUIUtils, $filter, CoreService, Chart, entity, $uibModalInstance) {
        var vm = this;

        //Functions
        vm.clear = clear;
        vm.save = save;

        //Variable
        vm.chart = entity;
        loadAll();

        function loadAll() {
            Bed.getBedsByIds({ids: CoreService.getUnassignedBeds()}, function (result) {
                vm.beds = result;
            });
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if(vm.chart){
                prepareAssign(vm.chart);
                Chart.update(vm.chart, onSaveSuccess, onSaveError);

            }
        }

        function onSaveSuccess (result) {
            // $scope.$emit('bluebookApp:bedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function prepareAssign(chart) {
            chart.bed.actualChart = chart.id;
        }

    }
})();
