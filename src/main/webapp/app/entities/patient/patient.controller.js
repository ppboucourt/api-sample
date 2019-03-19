(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientController', PatientController);

    PatientController.$inject = ['chart', 'Chart', '$state', 'CoreService', 'ChartToLevelCare', '$rootScope', 'lodash',
        '$scope', 'PatientMedication'];

    function PatientController(chart, Chart, $state, CoreService, ChartToLevelCare, $rootScope, _, $scope, PatientMedication) {
        var vm = this;

        // //Functions
        // vm.loadAll = loadAll;
        vm.updateWaitingChart = updateWaitingChart;
        // vm.updateWaitingChartAndPreloadForms = updateWaitingChartAndPreloadForms;

        vm.chart = chart;
        vm.generatePDF = function (id) {
            PatientMedication.patientMedicationById({"id": id}, function (data) {
                console.log("data", data);
            })
        }

        $rootScope.$on('allergiesDeleted', function (event, data) {
            _.remove(vm.chart.allergies, function (row) {
                return row.id === data.id;
            })
        });

        var unsubscribeUA = $rootScope.$on('bluebookApp:allergiesUpdate', function (event, result) {
            // Update allergies on the view
            var found = false;
            _.forEach(vm.chart.allergies, function (row, index) {
                if (row.id === result.id) {
                    found = true;
                    vm.chart.allergies[index] = result;
                }
            });
            if (!found) {
                vm.chart.allergies.push(result);
            }
        });
        $scope.$on('$destroy', unsubscribeUA);

        loadAll();
        function loadAll() {
            vm.chart.patient.age = CoreService.patientAge(new Date(vm.chart.patient.dateBirth).toString());
            // ChartToLevelCare.byChart({id: vm.chart.id}, function (result){
            //     var chartToLevelCares = result;
            //     if (chartToLevelCares.length) {
            //         debugger;
            //         var i = 0;
            //         for(i = 0; i < chartToLevelCares.length && !chartToLevelCares[i].endDate; i ++){
            //             vm.chart.loc = chartToLevelCares[i];
            //             vm.chart.loc.daysLoc = vm.chart.loc.daysLoc ? vm.chart.loc.daysLoc : 0;
            //         }
            //     }
            // });

            if (vm.chart.typePaymentMethods.category == 'Insurance') {
                if (!vm.chart.insurances.length)
                    addFirstInsurance();
                vm.gotInsurance = true;
            }
        }

        // //Update the waitingRoom on the Chart
        function updateWaitingChart() {
            vm.isSaving = true;
            Chart.updateWaiting({id: chart.id}, onUpdateWaitingSuccess, onUpdateWaitingError)
        }

        // //Update the waitingRoom on the Chart and Preload Forms on Chart
        // function updateWaitingChartAndPreloadForms() {
        //     vm.isSaving = true;
        //     Chart.updateWaitingWithForms(vm.chart, onUpdateWaitingSuccess, onUpdateWaitingError)
        // }

        function onUpdateWaitingSuccess(result) {
            vm.isSaving = !vm.isSaving;
            $state.go($state.current, {}, {reload: true}); //second parameter is for $stateParams
            // keepActiveTab(0);
        }

        function onUpdateWaitingError(error) {
            vm.isSaving = !vm.isSaving;
        }

    }
})();
