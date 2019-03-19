(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationPresNewDialogController', PatientMedicationPresNewDialogController);

    PatientMedicationPresNewDialogController.$inject = ['Routes', 'ROLES', 'Employee', 'Via', 'PatientMedication', 'problems',
        'MedicationsSearch', 'Icd10Search', 'patientMedication', 'patientMedicationPres', 'DTOptionsBuilder', 'DTColumnDefBuilder', '$uibModalInstance'];

    function PatientMedicationPresNewDialogController(Routes, ROLES, Employee, Via, PatientMedication, problems, MedicationsSearch,
                                                      Icd10Search, patientMedication, patientMedicationPres, DTOptionsBuilder, DTColumnDefBuilder, $uibModalInstance) {
        var vm = this;

        vm.vias = Via.query();
        vm.employees = Employee.employeesCorpPhysician();
        vm.routes = Routes.query();

        vm.patientMedication = patientMedication;
        vm.patientMedicationPres = patientMedicationPres;

        vm.is_new = vm.patientMedicationPres.patientMedicationTakes.length == 0;
        angular.copy(problems.problems, vm.problems = []);
        angular.copy(problems.problems, vm.problemsChk = []);

        vm.form = {};
        vm.formProblem = {};
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.staringDate = false;

        vm.startOptions = {
            minDate: new Date(),
        };

        vm.endOptions = {
            minDate: new Date(),
        };

        vm.dtOptions = DTOptionsBuilder.newOptions()
            .withPaginationType('full_numbers');

        vm.change = function () {
            if (vm.isSchedule() && !vm.patientMedicationPres.asNeeded) {
                vm.scheduleMedicine();
            }
        }

        vm.isSchedule = function () {
            if (vm.patientMedicationPres.medications && vm.patientMedicationPres.hours &&
                vm.patientMedicationPres.staringDate != null && vm.patientMedicationPres.endDate != null) {
                return true;
            } else {
                return false;
            }
        }

        vm.asneeeded = function () {
            if (vm.patientMedicationPres.asNeeded) {
                vm.patientMedicationPres.staringDate = null;
                vm.patientMedicationPres.endDate = null;
                vm.patientMedicationPres.patientActionTake = [];
            }
        }

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.setAsDischargeDate = function () {
            vm.patientMedicationPres.endDate = vm.patientMedication.chart.dischargeDate;
            vm.change();
        }

        vm.openCalendar = openCalendar;
        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.medications = [];
        vm.getMedications = getMedications;
        function getMedications(query) {
            if (query && query.length > 2) {
                MedicationsSearch.query({query: query}, function (result) {
                    vm.medications = result;
                    console.log(vm.medications);
                });
            }
        }

        vm.diagnoses = [];
        vm.getDiagnoses = getDiagnoses;
        function getDiagnoses(query) {
            if (query && query.length > 2) {
                Icd10Search.query({query: query}, function (result) {
                    vm.diagnoses = result;
                });
            }
        }

        vm.scheduleMedicine = scheduleMedicine;

        function scheduleMedicine() {
            // debugger
            var patientMedicationCopy = {};
            angular.copy(vm.patientMedication, patientMedicationCopy);
            //angular.copy([vm.patientMedicationPres], patientMedicationCopy.patientMedicationPress);
            patientMedicationCopy.patientMedicationPress = [vm.patientMedicationPres];
            PatientMedication.schedule(patientMedicationCopy, function (data) {
                vm.patientMedicationPres = data.patientMedicationPress[0];
            }, function (data) {
                alert('Error');
            });
        }

        vm.accept = function () {
            if (vm.patientMedicationPres.asNeeded) {
                vm.patientMedicationPres.staringDate = '';
                vm.patientMedicationPres.endDate = '';
            }

            vm.patientMedication.patientMedicationPress = [vm.patientMedicationPres];//[{patientMedicationPres: vm.patientMedicationPres, is_new: vm.is_new}];

            $uibModalInstance.close(vm.patientMedication);
        }

        vm.addProblemDiagnosis = function (index, problemsChk) {
            if (vm.problemsChk[index].in) {
                var findMedPres = _.find(vm.patientMedicationPres.icd10s, function (row) {
                    return row.id === problemsChk.id;
                })
                if (!findMedPres) {
                    vm.patientMedicationPres.icd10s.push(problemsChk);
                }
            } else {
                // vm.patientMedicationPres.icd10s.splice(i, 1);
                _.remove(vm.patientMedicationPres.icd10s, function (row, key) {
                    return row.id === problemsChk.id;
                })
            }

            vm.change();
        }
    }
})();
