(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderTestNewDialogController', PatientOrderTestNewDialogController);

    PatientOrderTestNewDialogController.$inject = ['problems', 'LabCompendiumSearch', 'Icd10Search', 'OrderFrequency', '$http', 'patientOrder', 'patientOrderTest', 'PatientOrder', '$uibModalInstance'];

    function PatientOrderTestNewDialogController(problems, LabCompendiumSearch, Icd10Search, OrderFrequency, $http, patientOrder, patientOrderTest, PatientOrder, $uibModalInstance) {
        var vm = this;

        vm.patientOrder = patientOrder;
        vm.patientOrderTest = patientOrderTest;
        vm.is_new = vm.patientOrderTest.patientOrderItems.length == 0;
        angular.copy(problems.problems, vm.problems = []);
        angular.copy(problems.problems, vm.problemsChk = []);
        vm.orderfrequencies = OrderFrequency.query();

        vm.form = {};
        vm.formProblem = {};
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.staringDate = false;

        vm.startOptions = {
            // minDate: new Date()
        }

        vm.endOptions = {
            // minDate: new Date()
        }

        vm.change = function () {
            if (vm.patientOrderTest.labCompendium  &&
                vm.patientOrderTest.staringDate && vm.patientOrderTest.endDate &&
                vm.patientOrderTest.icd10s && vm.patientOrderTest.icd10s.length >0) {
                vm.scheduleTest();
            }

            vm.endOptions.minDate = vm.patientOrderTest.staringDate;
        }

        vm.changeValue = function () {
            vm.patientOrderTest.endDate = vm.patientOrder.chart.dischargeDate;
            vm.change();
        }

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.openCalendar = openCalendar;
        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.compendiums = [];
        vm.getCompendium = getCompendium;
        function getCompendium(query) {
            if (query && query.length > 2) {
                LabCompendiumSearch.query({query: query}, function (result) {
                    vm.compendiums = result;
                });
            }
        }

        vm.diagnoses = [];
        vm.getDiagnoses = getDiagnoses;
        function getDiagnoses(query) {
            if (query && query.length > 3) {
                Icd10Search.query({query: query}, function (result) {
                    vm.diagnoses = result;
                });
            }
        }

        vm.scheduleTest = scheduleTest;

        function scheduleTest() {
            var orderCopy = {};
            angular.copy(vm.patientOrder, orderCopy);
            angular.copy([vm.patientOrderTest], orderCopy.patientOrderTests);

            PatientOrder.schedule(orderCopy, function (data) {
                vm.patientOrderTest = data.patientOrderTests[0];
            }, function () {
                alert('Error');
            });
        }

        vm.accept = function () {
            $uibModalInstance.close({patientOrderTest: vm.patientOrderTest, is_new: vm.is_new});
        }

        vm.addProblemDiagnosis = function (index) {
            var i = vm.patientOrderTest.icd10s.indexOf(vm.problems[index]);

            if (vm.problemsChk[index].in) {
                if (i == -1) {
                    vm.patientOrderTest.icd10s.push(vm.problems[index]);
                }
            } else {
                if (i > -1) {
                    vm.patientOrderTest.icd10s.splice(i, 1);
                }
            }

            vm.change();
        }
    }
})();
