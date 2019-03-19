(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FormInvoiceController', FormInvoiceController);

    FormInvoiceController.$inject = ['$state', '$stateParams', '$sessionStorage', 'Evaluation', 'ChartToForm',
        'Base64', 'CoreService', '$uibModal', 'Contacts', '$sce',
        'SystemService', '$q', 'patient', '$scope', 'Forms', 'chartToForm'];

    function FormInvoiceController ( $state, $stateParams, $sessionStorage, Evaluation, ChartToForm,
                                     Base64, CoreService, $uibModal, Contacts, $sce,
                                     SystemService, $q, patient, $scope, Forms, chartToForm) {
        var vm = this;
        //Functions
        vm.backDetailsBasic = backDetailsBasic;
        vm.sign = sign;
        vm.print = print;
        vm.saveConsentHtml = saveConsentHtml;
        vm.checkConsent = checkConsent;
        vm.next = next;
        vm.previous = previous;

        //Variable
        vm.formTemplate = 'Loading...';
        vm.consentForm = true;
        vm.facility = CoreService.getCurrentFacility();
        vm.chartToForm = chartToForm;
        vm.signatories = [];
        vm.signatory = {};
        vm.chart = {};
        vm.patient = {};
        vm.typeSign = null;
        vm.approve = false;
        vm.revoke = false;
        vm.revoked = false;
        vm.attachedFiles = {};
        vm.imageType = 'image';
        vm.applicationType = 'application';
        vm.listForms = [];

        $scope.currentPage = 0;

        vm.attachFile = function (result, successResult, errorResult) {
            SystemService.attachConsent(result, successResult, errorResult);
        };

        vm.deleteFile = function (fileId) {
            var defer = $q.defer();
            SystemService.deleteConsentFile({id: fileId},
                function success(data) {
                    defer.resolve(data);
                }, function error(error) {
                    defer.reject(error);
                });
            return defer.promise;
        };

        loadAll();
        function loadAll() {
            // getChartToForm().then(function (result) {
            //     vm.chartToForm = result;
            //     signAndRevokeValidation();
            // });
            // signAndRevokeValidation();

            ChartToForm.byChartPatientProcess({chId: $stateParams.id, ppId: $sessionStorage.patientProcess.id}, function (result) {
                if (result.length > 0) {
                    vm.listForms = result;
                    $scope.numberOfPages = result.length;
                    $scope.currentPage = $sessionStorage.indexSelected;
                    parser(vm.listForms[$sessionStorage.indexSelected]).then(function (result) {
                        vm.chartToForm = result;
                        signAndRevokeValidation();
                    });
                }
            }, function (error) {
                console.log(error);
            });

            var facilityStreetTwo = vm.facility.streetTwo ? vm.facility.streetTwo : '';
            vm.facility.address1 = vm.facility.street + ' ' + facilityStreetTwo;
            vm.facility.address2 = vm.facility.city + ' ' + vm.facility.state + ' ' + vm.facility.zip;

            vm.patient = patient;
            var middleName = vm.patient.middleName?vm.patient.middleName:'';
            vm.patient.fullName = vm.patient.firstName + ' ' + middleName + ' ' + vm.patient.lastName;

            vm.patient = patient;
            var middleName = vm.patient.middleName?vm.patient.middleName:'';
        }

        // function getChartToForm() {
        //     var defer = $q.defer();
        //     ChartToForm.getVO({id: $sessionStorage.consentId}, function (result) {
        //         defer.resolve(result);
        //         // vm.chartToForm = result;
        //     }, function (error) {
        //         defer.reject(error);
        //         console.log(error);
        //     });
        //     return defer.promise;
        // }

        function parser(form) {
            var defer = $q.defer();
            vm.formTemplate = 'Loading...';
            vm.chartToForm = {};
            $sessionStorage.consentId = form.id;
            ChartToForm.getVO({id: form.id}, function (result) {
                Forms.parserForm({id: form.id}, function (result) {
                    vm.formTemplate = vm.chartToForm.htmlData ? Base64.decode(vm.chartToForm.htmlData) : Base64.decode(result.body);
                    vm.formTemplate = $sce.trustAsHtml(vm.formTemplate);
                    setCheckBox();
                    setTextBox();
                }, function (error) {
                    console.log('Error in the parser:' + error);
                });
                defer.resolve(result);
            });
            return defer.promise;
        }

        function setCheckBox() {
            angular.element(document).ready(function () {
                $('#formTemplate input[type="checkbox"]').each(function (element) {
                    if (!vm.chartToForm.htmlData) {
                        $(this).attr('id', this.name + '_' + element);
                        // console.log(element);
                    } else {
                        $(this).attr("disabled", "disabled");
                    }
                });
                $('#formTemplate input[type="checkbox"]').on('click', function (event) {
                    if (event.target) {
                        $(this).removeAttr("checked");
                    }
                });

            });
        }

        function setTextBox() {
            angular.element(document).ready(function () {
                $('#formTemplate table input[type="text"]').each(function () {
                    if (this) {//Set a width 100% to the text input inside a table
                        $(this).css('width', '100%');
                    }
                });

                $('#formTemplate input[type="text"]').each(function (element) {
                    if (!vm.chartToForm.htmlData) {
                        $(this).attr('id', this.name + '_' + element);
                        // console.log(element);
                    } else {
                        $(this).attr("disabled", "disabled");
                    }
                });
                $('#formTemplate input[type="text"]').focusout(function (event) {
                    if (event.target) {
                        $(this).attr("value", this.value);
                    }
                });

            });
        }

        function checkConsent(event) {
            console.log(event);
        }

        function backDetailsBasic() {
            delete $sessionStorage.typeForm;
            window.history.back();
            //$state.go('patient-forms', {}, {reload: true});
        }

        var modalFormInstance = null;

        function sign(value) {
            vm.typeSign = value;
            if (modalFormInstance !== null) return;
            modalFormInstance = $uibModal.open({
                templateUrl: 'app/components/udt/sign/sign-forms.html',
                controller: 'SignFormController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    form: ['ChartToForm', '$stateParams', '$sessionStorage', function (ChartToForm, $stateParams, $sessionStorage) {
                        return ChartToForm.get({id: $sessionStorage.consentId}).$promise;
                    }],
                    guarantor: ['Contacts', function (Contacts) {
                        return Contacts.guarantor({id: $stateParams.id}).$promise;
                    }],
                    type: [function () {
                        return value;
                    }],
                    html: [function () {
                        if (!vm.consentForm.htmlData) {
                            return $('#formTemplate').html();
                        } else
                            return disabled;
                    }]
                }
            });
            modalFormInstance.result.then(
                resetModalForm,
                resetModalForm
            );
        }

        var resetModalForm = function (result) {
            modalFormInstance = null;
            if (result != 'cancel') {
                if (vm.typeSign = 'approve')
                    collectSignaturePatientData(result);
                if (vm.typeSign = 'revoke' && result.guarantor)
                    collectSignatureGuarantorData(result, result.guarantor);
                $state.go('form-invoice', {}, {reload: true});
            } else
                $state.go('form-invoice', {}, {reload: false});
        };

        function collectSignaturePatientData(data) {
            var signData = {};
            signData.fullName = vm.patient.fullName;
            signData.signature = data.signature;
            signData.ip = data.ip;
            signData.date = data.date;
            signData.supervised = CoreService.getCurrentEmployee();
            signData.signType = vm.typeSign ? vm.typeSign : null;

            return signData;
        }

        function collectSignatureGuarantorData(data, guarantor) {
            var signData = {};
            signData.fullName = guarantor;
            signData.signature = data.signature;
            signData.ip = data.ip;
            signData.date = data.date;
            signData.supervised = CoreService.getCurrentEmployee();
            signData.signType = vm.typeSign ? vm.typeSign : null;

            return signData;
        }

        function print() {
            window.print();
        }

        function signAndRevokeValidation() {
            vm.approve = false;
            vm.revoke = false;
            vm.signatories = [];
            if (vm.chartToForm.signaturePatient) {
                vm.typeSign = 'approve';
                vm.signatories.push(collectSignaturePatientData(vm.chartToForm.signaturePatient));
            }
            if (vm.chartToForm.signatureGuarantor) {
                vm.typeSign = 'approve';
                vm.signatories.push(collectSignatureGuarantorData(vm.chartToForm.signatureGuarantor, vm.chartToForm.guarantor));
            }
            if (vm.chartToForm.revocationPatient) {
                vm.typeSign = 'revoke';
                vm.signatories.push(collectSignaturePatientData(vm.chartToForm.revocationPatient));
            }
            if (vm.chartToForm.revocationGuarantor) {
                vm.typeSign = 'revoke';
                vm.signatories.push(collectSignatureGuarantorData(vm.chartToForm.revocationGuarantor, vm.chartToForm.guarantor));
            }

            if (vm.chartToForm.patientSignatureRequired && vm.chartToForm.guarantorSignatureRequired) {
                if (!vm.chartToForm.signaturePatient || !vm.chartToForm.signatureGuarantor) {
                    vm.approve = true;
                }
            } else {
                if (vm.chartToForm.patientSignatureRequired && !vm.chartToForm.signaturePatient) {
                    vm.approve = true;
                }
                if (vm.chartToForm.guarantorSignatureRequired && !vm.chartToForm.signatureGuarantor) {
                    vm.approve = true;
                }
            }
            if (vm.chartToForm.allowRevocation) {
                if ((vm.chartToForm.patientSignatureRequired && vm.chartToForm.signaturePatient) && (vm.chartToForm.guarantorSignatureRequired && vm.chartToForm.signatureGuarantor)) {
                    if ((vm.chartToForm.patientSignatureRequired && !vm.chartToForm.revocationPatient) || ( vm.chartToForm.guarantorSignatureRequired && !vm.chartToForm.revocationGuarantor)) {
                        vm.revoke = true;
                    } else {
                        vm.revoked = true;
                    }
                }
            }
        }

        // $('#formTemplate').onclick(function () {
        //     $('#formTemplate input[type="checkbox"]').attr("checked", "checked");
        // })

        function saveConsentHtml() {
            vm.consentForm = $('#formTemplate').html();
        }

        function next() {
            $scope.currentPage = $scope.currentPage+1;
            $sessionStorage.indexSelected = $scope.currentPage;
            parser(vm.listForms[$scope.currentPage]).then(function (result) {
                vm.chartToForm = result;
                signAndRevokeValidation();
            });
        }

        function previous() {
            $scope.currentPage = $scope.currentPage-1;
            $sessionStorage.indexSelected = $scope.currentPage;
            parser(vm.listForms[$scope.currentPage]).then(function (result) {
                vm.chartToForm = result;
                signAndRevokeValidation();
            });
        }

    }
})();
