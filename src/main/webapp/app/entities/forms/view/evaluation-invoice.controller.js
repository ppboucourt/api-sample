(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationInvoiceController', EvaluationInvoiceController);

    EvaluationInvoiceController.$inject = ['$state', '$stateParams', '$sessionStorage', 'Evaluation',
        'CoreService', '$uibModal', 'entity', 'EvaluationSignature', '$timeout', '$rootScope', 'Base64', 'Chart',
        'SystemService', '$q', 'chart', 'patient'];

    function EvaluationInvoiceController($state, $stateParams, $sessionStorage, Evaluation,
                                         CoreService, $uibModal, entity, EvaluationSignature, $timeout, $rootScope, Base64, Chart,
                                         SystemService, $q, chart, patient) {


        $timeout(function () {
            // $('input').bind("cut paste", function (e) {//paste
            //     e.preventDefault();
            // });
            //
            // $(function () {
            //     $('input').keydown(function (e) {
            //         var key = e.keyCode;
            //         if (!isValid(key)) {
            //             e.preventDefault();
            //         }
            //     });
            // });
            //
            // $('textarea').bind("cut paste", function (e) {//paste
            //     e.preventDefault();
            // });
            //
            // $(function () {
            //     $('textarea').keydown(function (e) {
            //         var key = e.keyCode;
            //         if (!isValid(key)) {
            //             e.preventDefault();
            //         }
            //     });
            // });
        }, 1000);


        function isValid(key) {
            console.log(key);
            return (key == 9) || (key == 8) ||
                (key == 10) || (key == 13) ||
                (key == 32) ||
                (key >= 35 && key <= 57) ||
                (key == 59) ||
                (key >= 63 && key <= 90) ||
                (key >= 96 && key <= 122) ||
                (key == 188) ||
                (key == 189) || (key == 190) || (key == 191);//Linux
        }

        var vm = this;
        vm.evaluation = entity;
        vm.$stateParams = $stateParams;
        vm.isSaving = false;


        vm.attachFile = attachFile();

        vm.$timeout = $timeout;


        vm.visible = false;

        vm.patient = patient;
        var middleName = vm.patient.middleName?vm.patient.middleName:'';
        vm.patient.fullName = vm.patient.firstName + ' ' + middleName + ' ' + vm.patient.lastName;
        vm.evaluationSignatures = [];
        vm.signatories = [];
        vm.SIGN_TYPE = {APPROVED: 'Approved', REVIEWED: 'Reviewed'};
        vm.EVALUETION_STATUS = {
            PENDING: 'Pending',
            IN_PROCESS: 'InProcess',
            PENDING_REVIEW: 'PendingReview',
            FINALIZED: 'Finalized'
        };

        vm.attachFile = function (result, successResult, errorResult) {
            SystemService.attachEvaluation(result, successResult, errorResult);
        }

        vm.deleteFile = function (fileId) {
            var defer = $q.defer();
            SystemService.deleteEvaluationFile({id: fileId},
                function success(data) {
                    defer.resolve(data);
                }, function error(error) {
                    defer.reject(error);
                });
            return defer.promise;
        }


        function loadAll() {
            vm.evaluation.jsonTemplate = vm.evaluation.jsonTemplate ? angular.fromJson(Base64.decode(vm.evaluation.jsonTemplate)) : {};
            vm.evaluation.jsonData = vm.evaluation.jsonData ? angular.fromJson(Base64.decode(vm.evaluation.jsonData)) : {};

            vm.isAlreadySaved = false;

            Chart.get({id: vm.$stateParams.id}, function (data) {
                vm.evaluation.chart = data;

                if (vm.evaluation && vm.evaluation.chart && vm.evaluation.chart.patient) {
                    vm.evaluation.chart.patient.fullName = vm.evaluation.chart.patient.firstName + ' ' + vm.evaluation.chart.patient.lastName;
                }
            }, function (error) {
                console.log(error);
            });


            vm.extractRoles = function (rolesString) {

                var roles = [];

                if (rolesString) {
                    var tmp = rolesString.split(",");
                    for (var i = 0; i < tmp.length; i++) {
                        roles.push(tmp[i].trim());
                    }
                }

                return roles;
            };

            vm.backDetailsBasic = backDetailsBasic;
            vm.facility = CoreService.getCurrentFacility();

            var facilityStreetTwo = vm.facility.streetTwo ? vm.facility.streetTwo : '';
            vm.facility.address1 = vm.facility.street + ' ' + facilityStreetTwo;
            vm.facility.address2 = vm.facility.city + ' ' + vm.facility.state + ' ' + vm.facility.zip;

            vm.eSignRoles = vm.extractRoles(vm.evaluation.rolesSign); //Evaluation Sign Roles
            vm.eReviewRoles = vm.extractRoles(vm.evaluation.rolesReview); //Evaluation Review Roles

            vm.loginRoles = CoreService.getCurrentEmployee().user.authorities; // [{name: "ROLE_NAME"}]

            vm.eSSignRoles = []; //Evaluation Signature Sign Roles
            vm.eSReviewRoles = []; //Evaluation Signature Review Roles


            if (vm.evaluation && vm.evaluation.jsonData) {
                for (var pro in vm.evaluation.jsonData) {
                    vm.isAlreadySaved = true;
                    break;
                }
            }

            EvaluationSignature.evaluationSignaturesByEvaluation({id: vm.evaluation.id}, function (data) {
                vm.evaluationSignatures = data;

                if (vm.evaluation.patientSignature) {//If required sign
                    vm.patientAlreadySigned = vm.evaluationSignatures && vm.evaluationSignatures.length > 0 ? true : false;
                } else {
                    vm.patientAlreadySigned = true;
                }

                if (vm.evaluationSignatures && vm.evaluationSignatures.length > 0) {

                    for (var i = 0; i < vm.evaluationSignatures.length; i++) {

                        if (!vm.evaluationSignatures[i].patient_signature) {// If

                            if (vm.evaluationSignatures[i].signType == vm.SIGN_TYPE.APPROVED) {
                                vm.eSSignRoles = vm.eSSignRoles.concat(vm.extractRoles(vm.evaluationSignatures[i].role));
                            } else {
                                vm.eSReviewRoles = vm.eSReviewRoles.concat(vm.extractRoles(vm.evaluationSignatures[i].role));
                            }
                        }
                    }

                }

                //ERoles - ESRoles = PossiblesRoles U LoginUserRoles
                vm.possibleRoles = [];
                for (var i = 0; i < vm.eSignRoles.length; i++) {
                    if (!isIn(vm.eSignRoles[i], vm.eSSignRoles)) {
                        vm.possibleRoles.push(vm.eSignRoles[i]);
                    }
                }

                vm.currentSignRoles = [];
                for (var i = 0; i < vm.possibleRoles.length; i++) {
                    if (isIn(vm.possibleRoles[i], vm.loginRoles)) {
                        vm.currentSignRoles.push(vm.possibleRoles[i]);
                    }
                }

                //EReviewRoles - ESReviewRoles = PossiblesReviewRoles U LoginUserRoles
                var possibleReviewRoles = [];
                for (var i = 0; i < vm.eReviewRoles.length; i++) {
                    if (!isIn(vm.eReviewRoles[i], vm.eSReviewRoles)) {
                        possibleReviewRoles.push(vm.eReviewRoles[i]);
                    }
                }

                vm.currentReviewRoles = [];
                for (var i = 0; i < possibleReviewRoles.length; i++) {
                    if (isIn(possibleReviewRoles[i], vm.loginRoles)) {
                        vm.currentReviewRoles.push(possibleReviewRoles[i]);
                    }
                }


                if (vm.evaluation.jsonTemplate && vm.evaluation.jsonTemplate.fields && vm.evaluation.jsonTemplate.fields.length > 0) {
                    vm.visible = true;
                }

            }, function (error) {
                console.log(error);
            });

        }

        function isIn(object, array) {

            if (array && array.length > 0) {
                for (var i = 0; i < array.length; i++) {
                    if (object == array[i] || object == array[i].name)
                        return true;
                }
            }

            return false;
        }

        function backDetailsBasic() {
            delete $sessionStorage.typeForm;
            window.history.back();
        }


        vm.extractRolesAsString = function (array) {
            var roles = "";

            for (var i = 0; i < array.length; i++) {
                if (roles == "") {
                    roles = array[i];
                } else {
                    roles = roles + ',' + array[i];
                }
            }
            return roles;
        }


        var modalFormInstance = null;

        vm.signEvaluation = function () {
            if (modalFormInstance !== null) return;
            modalFormInstance = $uibModal.open({
                templateUrl: 'app/entities/forms/view/evaluation-sign-dialog.html',
                controller: 'EvaluationSignController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'sm',
                resolve: {
                    form: ['$stateParams', 'CoreService', 'Employee', function ($stateParams, CoreService, Employee) {
                        vm.title = "Sign Evaluation";
                    }]
                }
            });
            modalFormInstance.result.then(
                resetModalForm,
                resetModalForm
            );
        }

        function setEvaluationData() {

            vm.evaluation.jsonTemplate = Base64.encode(JSON.stringify(vm.evaluation.jsonTemplate)); //Base64.encode
            vm.evaluation.jsonData = Base64.encode(JSON.stringify(vm.evaluation.jsonData));
            Base64.encode

        }

        var resetModalForm = function (result) {

            vm.isSaving = true;

            modalFormInstance = null;
            if (result == true) {
                var signature = {};
                signature.date = new Date();
                signature.ip = null;
                signature.createBy = CoreService.getCurrentEmployee().firtName;

                vm.evaluation.status = vm.EVALUETION_STATUS.PENDING_REVIEW;

                if ((vm.eReviewRoles == null || vm.eReviewRoles.length == 0)) {
                    vm.evaluation.status = vm.EVALUETION_STATUS.FINALIZED;
                }

                var evaluationSignature = {};
                evaluationSignature.evaluation = vm.evaluation;
                evaluationSignature.employee = CoreService.getCurrentEmployee();

                evaluationSignature.signature = signature;
                evaluationSignature.role = vm.extractRolesAsString(vm.currentSignRoles);
                evaluationSignature.delStatus = false;
                evaluationSignature.signType = vm.SIGN_TYPE.APPROVED;

                setEvaluationData();

                EvaluationSignature.save(evaluationSignature, function (data) {
                        vm.backDetailsBasic();
                    },
                    function (error) {
                        vm.isSaving = false;
                        console.log(error);
                    }
                );
            } else {
                vm.isSaving = false;
            }


        };


        var resetModalFormReview = function (result) {

            vm.isSaving = true;
            modalFormInstance = null;
            if (result == true) {
                var signature = {};
                signature.date = new Date();
                signature.ip = null;
                signature.createBy = CoreService.getCurrentEmployee().firtName;

                //Base64.encode(vm.evaluation.jsonTemplate);

                var evaluationSignature = {};
                evaluationSignature.evaluation = vm.evaluation;
                evaluationSignature.employee = CoreService.getCurrentEmployee();

                evaluationSignature.signature = signature;
                evaluationSignature.role = vm.extractRolesAsString(vm.currentReviewRoles);
                evaluationSignature.delStatus = false;
                evaluationSignature.signType = vm.SIGN_TYPE.REVIEWED;

                vm.evaluation.status = vm.EVALUETION_STATUS.FINALIZED;

                setEvaluationData();

                EvaluationSignature.save(evaluationSignature, function (data) {
                        vm.backDetailsBasic();
                    },
                    function (error) {
                        vm.isSaving = false;
                        console.log(error);
                    }
                );
            } else {
                vm.isSaving = false;
            }
        };

        var resetModalPatientSign = function (result) {
            vm.isSaving = true;

            if (result != 'cancel') {
                modalFormInstance = null;
                var array = result.split(',');
                var type = array[0];
                var sign = array[1];

                var signature = {};
                signature.date = new Date();
                signature.ip = null;
                signature.createBy = CoreService.getCurrentEmployee().firtName;
                signature.signature = sign;
                signature.signatureContentType = type;

                var evaluationSignature = {};

                if ((vm.evaluation.rolesSign == null || vm.evaluation.rolesSign.length == 0) && (vm.evaluation.rolesReview == null || vm.evaluation.rolesReview.length == 0)) {
                    vm.evaluation.status = vm.EVALUETION_STATUS.FINALIZED;
                } else if ((vm.evaluation.rolesSign == null || vm.evaluation.rolesSign.length == 0)) {//If no need employee sign
                    vm.evaluation.status = vm.EVALUETION_STATUS.PENDING_REVIEW;
                } else {
                    vm.evaluation.status = vm.EVALUETION_STATUS.IN_PROCESS;
                }


                evaluationSignature.evaluation = vm.evaluation;

                evaluationSignature.patientSignature = signature;
                evaluationSignature.delStatus = false;
                evaluationSignature.signType = vm.SIGN_TYPE.APPROVED;

                setEvaluationData();

                EvaluationSignature.save(evaluationSignature, function (data) {
                        vm.backDetailsBasic();
                    },
                    function (error) {
                        console.log(error);
                    }
                );
            } else {
                modalFormInstance = null;
                vm.isSaving = false;
            }
        };

        vm.reviewAndSign = function () {
            if (modalFormInstance !== null) return;
            modalFormInstance = $uibModal.open({
                templateUrl: 'app/entities/forms/view/evaluation-sign-review-dialog.html',
                controller: 'EvaluationSignController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'sm',
                resolve: {
                    form: ['$stateParams', 'CoreService', 'Employee', function ($stateParams, CoreService, Employee) {
                    }],
                }
            });
            modalFormInstance.result.then(
                resetModalFormReview,
                resetModalFormReview
            );
        }


        vm.patientSign = function () {
            if (modalFormInstance !== null) return;
            modalFormInstance = $uibModal.open({
                templateUrl: 'app/entities/forms/view/evaluation-patient-sign-dialog.html',
                controller: 'EvaluationPatientSignController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    form: ['$stateParams', 'CoreService', 'Employee', function ($stateParams, CoreService, Employee) {
                    }],
                }
            });
            modalFormInstance.result.then(
                resetModalPatientSign,
                resetModalPatientSign
            );
        }

        vm.saveEvaluation = function () {

            vm.isSaving = true;

            // setEvaluationData();

            // return ;
            Evaluation.update(vm.evaluation,
                function (data) {
                    vm.backDetailsBasic();
                },
                function (error) {
                    vm.isSaving = false;
                });
        };

        vm.$timeout(function () {
            loadAll();
        }, 250);

        function attachFile(file, data) {
            CoreService.attachment(file, data).then(function (result) {
                result.id = vm.evaluation.id;
                SystemService.attachEvaluation(result);
            });
        }


        vm.viewAndPrinter = function () {

            var popupWinindow = window.open('', '_blank', '');//fullscreen=yes, scrollbars=yes,menubar=no,toolbar=yes,location=no,status=no,titlebar=yes
            popupWinindow.document.open();
            popupWinindow.document.write('<html>' +
                '<head> ' +
                '<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">' +
                '<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.csss">' +
                '<title>View Evaluation</title>' +

                '<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>' +
                '<style type="text/css">' +
                '@media print { ' +
                '.no-print' +
                ' { ' +
                ' display: none !important;' +
                ' } ' +
                '}' +
                '</style>' +

                '</head> ' +

                '<body>' +
                '<input id="jsonTemplate" type="hidden" value="' + Base64.encode(JSON.stringify(vm.evaluation.jsonTemplate)) + '"></input>' +
                '<input id="jsonData" type="hidden" value="' + Base64.encode(JSON.stringify(vm.evaluation.jsonData)) + '"></input>' +

                '<div  style="margin-left: 1%; margin-right: 1%"   class="row" id="content" >' +

                '<div class="row">' +
                '<div class="col-xs-10">' +
                '<h2 class="page-header">' +
                '<i class="fa fa-globe"></i> Evaluation <b>' + vm.evaluation.name + '</b>' +
                '</h2>' +
                '</div>' +

                '<div class="col-xs-2 no-print" style="margin-top: 2%; >' +

                '<div class="box-footer no-print">' +
                '<button type="button" class="btn btn-danger pull-right" onclick="window.close()"><i class="fa fa-close" aria-hidden="true"> Close</i></button>' +
                '<button type="button"  style="margin-right: 10px!important;"  class="btn btn-warning pull-right" onclick="window.print()"><i class="fa fa-print" aria-hidden="true"> Print</i></button>' +
                '</div>' +
                '</div>' +
                '</div>' +


                '<div class="row">' +
                '<div class="col-xs-12">' +
                '<div class="col-md-9 col-sm-8 ">' +
                '<address>  ' +
                '<strong>' + vm.facility.name + '<br>' +
                vm.facility.address1 + ' <br>' +
                vm.facility.address2 + '<br>' +
                'Phone: ' + vm.facility.phone + '<br>' +
                'Web Site: ' + vm.facility.website +
                '</address>' +
                '</div>' +
                '<div class="col-md-3 col-sm-4">' +
                '<address>' +
                '<strong>' + vm.evaluation.chart.patient.fullName + '</strong><br>' +
                'DOB: ' + moment(vm.evaluation.chart.patient.dateBirth).format('MM/dd/yyyy') + '<br>' +
                'Mr. No: ' + vm.evaluation.chart.mrNo +
                '</address>' +
                '</div>' +
                '</div>' +
                '</div>' +

                '<p style="text-align: center"> <b>Evaluation Information</b></p>' +
                '<hr/>' +

                '<div class="row" id="generate"></div>' +

                '<hr/>' +
                '<div class="row"    style="margin-left: 2%; margin-right: 1%"  id="signarea">' + $('#signarea').html() + '</div>' +
                '<hr/>' +

                '<div class="box-footer no-print">' +
                '<button type="button" class="btn btn-danger pull-right" onclick="window.close()"><i class="fa fa-close" aria-hidden="true"> Close</i></button>' +
                '<button type="button"  style="margin-right: 10px!important;"  class="btn btn-warning pull-right" onclick="window.print()"><i class="fa fa-print" aria-hidden="true"> Print</i></button>' +
                '</div>' +

                '</div>' +
                '</body>' +
                '<script src="content/adminLTE/js/evaluationview.js"></script>' +
                '</html>');


        }

    }
})();
