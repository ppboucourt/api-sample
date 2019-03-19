(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationDialogController', EvaluationDialogController);

    EvaluationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Evaluation', 'TypePatientProcess', 'TypeEvaluation', 'EvaluationContent', 'EvaluationItems'];

    function EvaluationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Evaluation, TypePatientProcess, TypeEvaluation, EvaluationContent, EvaluationItems) {
        var vm = this;

        vm.evaluation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.typepatientprocesses = TypePatientProcess.query();
        vm.typeevaluations = TypeEvaluation.query();
        vm.evaluationcontents = EvaluationContent.query();
        vm.evaluationitems = EvaluationItems.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evaluation.id !== null) {
                Evaluation.update(vm.evaluation, onSaveSuccess, onSaveError);
            } else {
                Evaluation.save(vm.evaluation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:evaluationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPatient_signature = function ($file, evaluation) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        evaluation.patient_signature = base64Data;
                        evaluation.patient_signatureContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.daily_start_time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
