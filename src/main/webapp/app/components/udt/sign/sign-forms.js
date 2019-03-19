/**
 * Created by PpTMUnited on 2/21/2017.
 */
(function () {
    'use strict';

    angular
        .module('udt')
        .controller('SignFormController', SignFormController);

    SignFormController.$inject = [ '$uibModalInstance', 'DATA', '$scope', 'JSignature', 'ChartToForm', 'form', 'type', 'Base64', 'html', 'guarantor', '$stateParams' ];

    function SignFormController( $uibModalInstance, DATA, $scope, JSignature, ChartToForm, form, type, Base64, html, guarantor, $stateParams ) {
        var vm = this;

        //Functions
        vm.clear = clear;
        vm.save = save;


        //Variable
        vm.signForm = form;
        // vm.jsonData = JSON.parse(form.jsonData);
        vm.guarantor = guarantor;
        vm.signatories = [];


        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        loadAll();
        function loadAll() {
            if (type == 'approve') {
                if (!vm.signForm.signaturePatient && vm.signForm.patientSignatureRequired) {
                    vm.signForm.signatory = vm.signForm.signatory?vm.signForm.signatory:DATA.SIGNATORIES.PATIENT;
                    vm.signatories.push(DATA.SIGNATORIES.PATIENT);
                }
                if (!vm.signForm.signatureGuarantor && vm.signForm.guarantorSignatureRequired && vm.guarantor.id) {
                    vm.signForm.signatory = vm.signForm.signatory?vm.signForm.signatory:DATA.SIGNATORIES.GUARANTOR;
                    vm.signatories.push(DATA.SIGNATORIES.GUARANTOR);
                }

            }
            if (type == 'revoke') {
                if (!vm.signForm.revocationPatient && vm.signForm.patientSignatureRequired) {
                    vm.signForm.signatory = vm.signForm.signatory?vm.signForm.signatory:DATA.SIGNATORIES.PATIENT;
                    vm.signatories.push(DATA.SIGNATORIES.PATIENT);
                }
                if (!vm.signForm.revocationGuarantor && vm.signForm.guarantorSignatureRequired && vm.guarantor.id) {
                    vm.signForm.signatory = vm.signForm.signatory?vm.signForm.signatory:DATA.SIGNATORIES.GUARANTOR;
                    vm.signatories.push(DATA.SIGNATORIES.GUARANTOR);
                }

            }

        }

        function save() {

            if (!JSignature.getData(JSignature.exportTypes.NATIVE).length) {
                alert("Please sign and then press finish!!!");
            } else {
                var sign = JSignature.getData(JSignature.exportTypes.IMAGE_PNG_BASE64);
                var splitSign = [];
                if (type == 'approve'){
                    if(vm.signForm.signatory == DATA.SIGNATORIES.PATIENT){
                        vm.signForm.signaturePatient = {};
                        splitSign = sign.split(",", sign.length);
                        vm.signForm.signaturePatient.signature = splitSign[1];
                        vm.signForm.signaturePatient.signatureContentType = 'image/png';
                    }else{
                        vm.signForm.signatureGuarantor = {};
                        splitSign = sign.split(",", sign.length);
                        vm.signForm.signatureGuarantor.signature = splitSign[1];
                        vm.signForm.signatureGuarantor.signatureContentType = 'image/png';
                    }
                }else{
                    if (type == 'revoke') {
                        if(vm.signForm.signatory == DATA.SIGNATORIES.PATIENT){
                            vm.signForm.revocationPatient = {};
                            splitSign = sign.split(",", sign.length);
                            vm.signForm.revocationPatient.signature = splitSign[1];
                            vm.signForm.revocationPatient.signatureContentType = 'image/png';
                        }else{
                            vm.signForm.revocationGuarantor = {};
                            splitSign = sign.split(",", sign.length);
                            vm.signForm.revocationGuarantor.signature = splitSign[1];
                            vm.signForm.revocationGuarantor.signatureContentType = 'image/png';
                        }
                    }
                }
                if (!vm.signForm.htmlData)
                    vm.signForm.htmlData =  html;
                vm.signForm.chart = $stateParams.id;
                ChartToForm.saveSign(vm.signForm, onSaveSuccess, onSaveError);
            }
        }

        var onSaveSuccess = function (result) {
            $uibModalInstance.dismiss(result);
        };

        var onSaveError = function (error) {
            clear();
        };

    }
})();
