(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionPreNewDialogController', PatientActionPreNewDialogController);

    PatientActionPreNewDialogController.$inject = ['ROLES', 'CoreService', 'chart', 'Via', 'Employee', 'PatientAction', 'problems', 'Icd10Search', 'patientAction', 'patientActionPre', 'DTOptionsBuilder', 'DTColumnDefBuilder', '$uibModalInstance'];

    function PatientActionPreNewDialogController(ROLES, CoreService, chart, Via, Employee, PatientAction, problems, Icd10Search, patientAction, patientActionPre, DTOptionsBuilder, DTColumnDefBuilder, $uibModalInstance) {
        var vm = this;

        vm.chart = chart;
        vm.patientActionPre = {};
        vm.patientActionPre.asNeeded = false;
        console.log(chart);
        vm.patientAction = {};
        vm.patientAction.chart = vm.chart;
        vm.patientAction.employee = CoreService.getCurrentEmployee();

        for (var i = 0; i < vm.patientAction.employee.user.authorities.length; i++){
            if (vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE
            ) {
                vm.patientAction.signedBy = vm.patientAction.employee;
                vm.patientAction.signed = true;
                vm.patientAction.signatureDate = new Date();
            }
        }

        vm.problems = vm.chart.icd10S;
        vm.patientAction.actionStatus = 'SCHEDULED';

        vm.form = {};
        vm.save = save;
        vm.vias = Via.query();
        vm.employees = Employee.employeesCorpPhysician();

        vm.orderfrequencies = [];

        angular.copy(problems.problems, vm.problems = []);
        angular.copy(problems.problems, vm.problemsChk = []);

        vm.setAsDischargeDate = function(){
            vm.patientActionPre.endDate = vm.patientAction.chart.dischargeDate;
            vm.change();
        }




        vm.vias = Via.query();
        vm.employees = Employee.employeesCorpPhysician();

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
            if (vm.patientActionPre.staringDate && vm.patientActionPre.endDate && vm.patientActionPre.action &&
                vm.patientActionPre.hours  && !vm.patientActionPre.asNeeded) {
                vm.scheduleAction();
            }
        }

        vm.asneeeded = function () {
            if (vm.patientActionPre.asNeeded) {
                vm.patientActionPre.staringDate = null;
                vm.patientActionPre.endDate = null;
                vm.patientActionPre.hours = null;
                vm.patientActionPre.patientActionTakes = [];
            }
        }

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.openCalendar = openCalendar;
        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
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

        vm.scheduleAction = scheduleAction;

        function scheduleAction() {
            var patientActionCopy = {};
            angular.copy(vm.patientAction, patientActionCopy);
            patientActionCopy.patientActionPres = [vm.patientActionPre];

           // angular.copy([vm.patientActionPre], patientActionCopy.patientActionPres);

            PatientAction.schedule(patientActionCopy, function (data) {
                data.patientActionPres[0].staringDate = vm.patientActionPre.staringDate;//For force mantenaince the vm.staringDate

                vm.patientActionPre = data.patientActionPres[0];
            }, function () {
                alert('Error');
            });
        }

        vm.accept = function () {
            if (vm.patientActionPre.asNeeded) {
                vm.patientActionPre.staringDate = '';
                vm.patientActionPre.endDate = '';
            }

            vm.patientAction.patientActionPres = [];
            vm.patientAction.patientActionPres.push(vm.patientActionPre);

            save();
        }

        vm.addProblemDiagnosis = function (index) {
            var i = vm.patientActionPre.icd10s.indexOf(vm.problems[index]);

            if (vm.problemsChk[index].in) {
                if (i == -1) {
                    vm.patientActionPre.icd10s.push(vm.problems[index]);
                }
            } else {
                if (i > -1) {
                    vm.patientActionPre.icd10s.splice(i, 1);
                }
            }

            vm.change();
        }


        function save() {
            vm.isSaving = true;
            PatientAction.save(vm.patientAction, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess() {
            vm.isSaving = false;
            $uibModalInstance.close(true);
        }

        function onSaveError() {
            vm.isSaving = false;
        }

    }
})();
