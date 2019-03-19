(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationViewInvoiceController', EvaluationViewInvoiceController);

    EvaluationViewInvoiceController.$inject = ['$state', '$stateParams', '$sessionStorage', 'Evaluation', 'chart',
        'CoreService', '$uibModal', 'EvaluationSignature', '$timeout', '$rootScope', 'Base64', 'Chart', '$scope', 'patient'];

    function EvaluationViewInvoiceController($state, $stateParams, $sessionStorage, Evaluation, chart,
                                             CoreService, $uibModal, EvaluationSignature, $timeout, $rootScope, Base64, Chart, $scope, patient) {


        var vm = this;

        //Functions
        vm.next = next;
        vm.previous = previous;

        vm.evaluation = {};
        vm.$stateParams = $stateParams;
        vm.$timeout = $timeout;
        vm.listForms = [];

        vm.facility = CoreService.getCurrentFacility();

        var facilityStreetTwo = vm.facility.streetTwo ? vm.facility.streetTwo : '';
        vm.facility.address1 = vm.facility.street + ' ' + facilityStreetTwo;
        vm.facility.address2 = vm.facility.city + ' ' + vm.facility.state + ' ' + vm.facility.zip;
        vm.NOTHING = 'Nothing';
        vm.evaluationSignatures = [];
        vm.signatories=[];
        vm.SIGN_TYPE = { APPROVED:'Approved', REVIEWED: 'Reviewed'};
        vm.EVALUETION_STATUS= {PENDING: 'Pending', IN_PROCESS: 'InProcess', PENDING_REVIEW:'PendingReview', FINALIZED: 'Finalized'};
        vm.patient = patient;
        var middleName = vm.patient.middleName?vm.patient.middleName:'';
        vm.patient.fullName = vm.patient.firstName + ' ' + middleName + ' ' + vm.patient.lastName;

        vm.backDetailsBasic = backDetailsBasic;

        function backDetailsBasic() {
            delete $sessionStorage.typeForm;
            window.history.back();
        }

        loadAll();
        function loadAll() {

            Evaluation.byChartPatientProcess({chId: $stateParams.id, ppId: $sessionStorage.patientProcess.id}, function (result) {
                if (result && result.length > 0) {
                    vm.listForms = result;
                    $scope.numberOfPages = result.length;
                    $scope.currentPage = $sessionStorage.indexSelected;
                }
            });

            Evaluation.get({id: $stateParams.eid}, function (result) {
                vm.evaluation = result;
                vm.evaluation.chart = chart;
                if (vm.evaluation && vm.evaluation.chart && vm.evaluation.chart.patient) {
                    vm.evaluation.chart.patient.fullName = vm.evaluation.chart.patient.firstName + ' ' + vm.evaluation.chart.patient.lastName;
                }

                var jsonTemplate = JSON.parse(Base64.decode(vm.evaluation.jsonTemplate));
                var jsonData = JSON.parse(Base64.decode(vm.evaluation.jsonData));

                EvaluationSignature.evaluationSignaturesByEvaluation({id: vm.evaluation.id}, function (data2) {
                    vm.evaluationSignatures = data2;
                }, function(error){
                    console.log('Error retrieving Evaluation Signatures: ' + error);
                });

                $('#generate').html(renderEvaluation(jsonTemplate, jsonData));
            });
        }


        function renderEvaluation(jsonTemplate, jsonData) {

            var contentGenerate = '<div id="contentGenerate " class="" style="margin-left: 2%">';
            var fields = jsonTemplate.fields;

            for (var i = 0; i < fields.length; i++) {
                contentGenerate = contentGenerate + generateFieldView(fields[i], jsonData[fields[i].name]);
            }

            contentGenerate = contentGenerate + '<div>';
            return contentGenerate;
        }


        function generateFieldView(fieldObject, dataObject) {

            var text = '<label for="' + fieldObject.name + '">' + fieldObject.displayName + '</label><br>';
            text = text + generateContentByType(fieldObject, dataObject);

            return text;
        }


        function generateContentByType(fieldObject, dataObject) {


            if (fieldObject.type == 'text' || fieldObject.type == 'textarea' || fieldObject.type == 'email' || fieldObject.type == 'label' || fieldObject.type == 'paragraph') {
                var string = (dataObject && dataObject.length > 0) ? dataObject : vm.NOTHING;

                return '<p id="' + fieldObject.name + '" style="margin-left: 1%; text-align: left">' + string + '</p><br>';
            } else if (fieldObject.type == 'number') {

                var string = (dataObject == null || dataObject == undefined) ? vm.NOTHING : dataObject;

                return '<p id="' + fieldObject.name + '" style="margin-left: 1%; text-align: left">' + string + '</p><br>';

            } else if (fieldObject.type == 'password') {
                var l = dataObject ? dataObject.length : 0;

                if (l > 0) {
                    return '<p id="' + fieldObject.name + '" style="margin-left: 1%; text-align: left">' + '*'.repeat(l) + '</p><br>';
                } else {
                    return '<p id="' + fieldObject.name + '" style="margin-left: 1%; text-align: left">' + '----' + '</p><br>';
                    ;
                }

            } else if (fieldObject.type == 'checkbox') {

                return '<p id="' + fieldObject.name + '" style="margin-left: 1%; text-align: left">' + (dataObject ? 'Yes' : 'No') + '</p><br>';

            } else if (fieldObject.type == 'checkboxlist') {

                var string = '';
                for (var property in dataObject) {
                    if (dataObject.hasOwnProperty(property) && dataObject[property]) {

                        var options = fieldObject.options;
                        for (var i = 0; i < options.length; i++) {
                            if (property == options[i].value) {
                                string = string + (string.length == 0 ? options[i].text : ', ' + options[i].text);
                            }
                        }
                    }
                }

                //  string = string.length > 0 ?string: 'vm.NOTHING';

                return '<p id="' + fieldObject.name + '" style="margin-left: 1%; text-align: left">' + string + '</p><br>';

            } else if (fieldObject.type == 'radiobuttonlist' || fieldObject.type == 'selectlist') {

                var string = '';

                var options = fieldObject.options;
                for (var i = 0; i < options.length; i++) {
                    if (dataObject == options[i].value) {
                        string = string + (string.length == 0 ? options[i].text : ', ' + options[i].text);
                    }
                }

                string = string.length > 0 ? string : vm.NOTHING;

                return '<p id="' + fieldObject.name + '" style="margin-left: 1%; text-align: left">' + string + '</p><br>';

            } else if (fieldObject.type == 'customtable') {
                return generateContentCustomTable(fieldObject, dataObject);
            } else {
                return '<p id="' + fieldObject.name + '" style="margin-left: 1%; text-align: left">' + 'Not Implement at Moment.' + '</p><br>';
            }
        }


        function generateContentCustomTable(fieldObject, dataObject) {

            var columnHeaders = fieldObject.validation.columnHeaders;
            var rowHeaders = fieldObject.validation.rowHeaders;

            var contentTable = '<div class="table-responsive"> ';
            contentTable = contentTable +
                '<table class="table table-bordered" style="border: 1px solid #0A0A0A!important;">';

            var columnHeadersArray = columnHeaders.split(',');
            var rowHeadersArray = rowHeaders.split(',');

            if (rowHeaders) {
                contentTable = contentTable + '<tbody style="border: 1px solid #0A0A0A!important;">';

                if (columnHeaders && (columnHeaders.length + 1) && columnHeadersArray.length) {
                    contentTable = contentTable + '<tr style="border: 1px solid #0A0A0A!important;">';

                    //<td ng-if="field.schema.validation.rowHeaders && field.schema.validation.rowHeaders.length +1> field.schema.validation.rowHeaders.split(',').length  " style="width: 20%"></td>
                    if (rowHeaders && (rowHeaders.length + 1) > rowHeadersArray.length) {
                        contentTable = contentTable + '<td style="width: 20%; border: 1px solid #0A0A0A!important;"></td>';
                    }

                    //<td style="text-align: left"  ng-repeat="cHeader in field.schema.validation.columnHeaders.split(',') track by $index" ><strong>{{cHeader}}</strong></td><!--   It's in 0 cause into the matrix is the header-->
                    for (var i = 0; i < columnHeadersArray.length; i++) {
                        contentTable = contentTable + '<td style="text-align: left;border: 1px solid #0A0A0A!important;"><strong>' + columnHeadersArray[i] + '</strong></td>';
                    }

                    contentTable = contentTable + '</tr>';
                }


                for (var i = 0; i < rowHeadersArray.length; i++) {
                    contentTable = contentTable + '<tr style="border: 1px solid #0A0A0A!important;">';

                    if (rowHeaders.length + 1 > rowHeadersArray.length) {
                        contentTable = contentTable + '<td style="border: 1px solid #0A0A0A!important;"><strong>' + rowHeadersArray[i] + '</strong></td>';
                    }

                    for (var j = 0; j < columnHeadersArray.length; j++) {
                        contentTable = contentTable + '<td style="border: 1px solid #0A0A0A!important;"><div>' + cleanTDText(dataObject[i + '' + j]) + '</div></td>';
                    }

                    contentTable = contentTable + '</tr>';
                }

                contentTable = contentTable + '</tbody>';
            }

            contentTable = contentTable + '</table>';
            contentTable = contentTable + '</div>';
            return contentTable;
        }

        function cleanTDText(text) {

            var cleanText = text ? text : '';

            while(cleanText.indexOf("\\n") >= 0){
                cleanText = cleanText.replace("\\n", "<br>");
            }

            return cleanText;
        }

        function next() {
            $scope.currentPage = $scope.currentPage+1;
            // Render the evaluation and reassign evaluationId to sessionStorage here
        }

        function previous() {
            $scope.currentPage = $scope.currentPage-1;
            // Render the evaluation and reassign evaluationId to sessionStorage here
        }

    }
})();
