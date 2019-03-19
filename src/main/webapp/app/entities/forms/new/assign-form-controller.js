/**
 * Created by PpTMUnited on 4/5/2017.
 */
(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AssignFormController', AssignFormController);

    AssignFormController.$inject = [ '$scope', '$uibModalInstance', 'EvaluationTemplate', 'Forms', 'CoreService', 'patientProcess',
        'DTColumnBuilder', 'DTOptionsBuilder', '$q', 'GUIUtils', '$compile', 'ChartToForm', '$stateParams', 'Evaluation',
        'listForms', '$filter', 'levelCare'];

    function AssignFormController ( $scope, $uibModalInstance, EvaluationTemplate, Forms, CoreService, patientProcess,
                                    DTColumnBuilder, DTOptionsBuilder, $q, GUIUtils, $compile, ChartToForm, $stateParams, Evaluation,
                                    listForms, $filter, levelCare) {
        var vm = this;

        // vm.typeRace = entity;

        //Functions
        vm.clear = clear;
        vm.toggleAllConsents = toggleAllConsents;
        vm.toggleOneConsent = toggleOneConsent;
        vm.toggleAllEvaluations = toggleAllEvaluations;
        vm.toggleOneEvaluations = toggleOneEvaluations;
        vm.assignForms = assignForms;
        vm.keepActiveTab = keepActiveTab;
        vm.search = search;

        //Variable
        vm.activeTab = 0;
        vm.consentFormsCopy = [];
        vm.evaluationsCopy = [];

        function keepActiveTab($index) {
            vm.activeTab = $index;
        }

        vm.selectedConsents = {};
        vm.selectedEvaluations = {};

        function clear () {
            $uibModalInstance.dismiss();
        }

        function onSaveSuccess (result) {
            // $scope.$emit('bluebookApp:typeRaceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function search() {
            vm.dtInstanceConsentForms.reloadData();
            vm.dtInstanceEvaluations.reloadData();
        }

        vm.dtInstanceConsentForms = {};

        vm.dtOptionsConsentForms = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                if (levelCare) {
                    Forms.levelCare(
                        {ppId: patientProcess.id, facId: CoreService.getCurrentFacility().id, lcId: levelCare},
                        function(result) {
                            vm.consentForms = onlyOneConsentForm(result);
                            angular.copy(vm.consentForms, vm.consentFormsCopy);
                            defer.resolve(vm.consentForms);
                        });
                }else {
                    Forms.patientProcess({ppId: patientProcess.id, facId: CoreService.getCurrentFacility().id}, function(result) {
                        vm.consentForms = onlyOneConsentForm(result);
                        angular.copy(vm.consentForms, vm.consentFormsCopy);
                        defer.resolve(vm.consentForms);
                    });
                }

            }else {
                defer.resolve($filter('filter')(vm.consentForms, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            })
            .withOption('headerCallback', function(header) {
                if (!vm.headerCompiled) {
                    vm.headerCompiled = true;
                    $compile(angular.element(header).contents())($scope);
                }
            });

        var titleHtmlConsents = '<input type="checkbox" ng-model="vm.selectAllConsents" ng-change="vm.toggleAllConsents()">';

        vm.dtColumnsConsentForms = [
            DTColumnBuilder.newColumn(null).withTitle(titleHtmlConsents).notSortable().renderWith(function(data, type, full, meta) {
                vm.selectedConsents[full.id] = false;

                return '<input type="checkbox" ng-model="vm.selectedConsents[' + data.id + ']" ng-click="vm.toggleOneConsent()">';
            }),
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Patient Process').renderWith(function (data, type, full) {
                return data.patientProcess ? data.patientProcess : "Empty";
            }),
            DTColumnBuilder.newColumn('patientSignatureRequired').withTitle('Patient Sig.').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn('guarantorSignatureRequired').withTitle('Guarantor Sig.').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            })
            // DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
            //     .renderWith(actionsHtmlForms)
        ];

        vm.dtInstanceEvaluations = {};

        vm.dtOptionsEvaluations = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                if (levelCare) {
                    EvaluationTemplate.levelCare(
                        {ppId: patientProcess.id, facId: CoreService.getCurrentFacility().id,
                            lcId: levelCare},
                        function(result) {
                            vm.evaluations = onlyOneEvaluation(result);
                            angular.copy(vm.evaluations, vm.evaluationsCopy);
                            defer.resolve(vm.evaluations);
                        });
                }else {
                    EvaluationTemplate.patientProcess({ppId: patientProcess.id, facId: CoreService.getCurrentFacility().id}, function(result) {
                        vm.evaluations = onlyOneEvaluation(result);
                        angular.copy(vm.evaluations, vm.evaluationsCopy);
                        defer.resolve(vm.evaluations);
                    });
                }
            }else {
                defer.resolve($filter('filter')(vm.evaluations, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            })
            .withOption('headerCallback', function(header) {
                if (!vm.headerCompiledEvluations) {
                    vm.headerCompiledEvluations = true;
                    $compile(angular.element(header).contents())($scope);
                }
            });

        var titleHtmlEvaluations = '<input type="checkbox" ng-model="vm.selectAllEvaluations" ng-change="vm.toggleAllEvaluations()">';

        vm.dtColumnsEvaluations = [
            DTColumnBuilder.newColumn(null).withTitle(titleHtmlEvaluations).notSortable().renderWith(function(data, type, full, meta) {
                vm.selectedEvaluations[full.id] = false;
                return '<input type="checkbox" ng-model="vm.selectedEvaluations[' + data.id + ']" ng-click="vm.toggleOneEvaluations()">';
            }),
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Patient Process').renderWith(function (data, type, full) {
                return data.patientProcess ? data.patientProcess : "Empty";
            }),
            DTColumnBuilder.newColumn('enabled').withTitle('Enabled').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            // DTColumnBuilder.newColumn('patientSignature').withTitle('Patient Signature'),
            DTColumnBuilder.newColumn('onlyOne').withTitle('Only One').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            })
            // DTColumnBuilder.newColumn('billable').withTitle('Billable').renderWith(function (data, type, full) {
            //     return GUIUtils.colorHtmlYesNo(data);
            // })
        ];

        function toggleAllConsents () {
            for (var id in vm.selectedConsents) {
                if (vm.selectedConsents.hasOwnProperty(id)) {
                    vm.selectedConsents[id] = vm.selectAllConsents;
                }
            }
        }

        function toggleOneConsent () {
            for (var id in vm.selectedConsents) {
                if (vm.selectedConsents.hasOwnProperty(id)) {
                    if(!vm.selectedConsents[id]) {
                        vm.selectAllConsents = false;
                        return;
                    }
                }
            }
            vm.selectAllConsents = true;
        }

        function toggleAllEvaluations () {
            for (var id in vm.selectedEvaluations) {
                if (vm.selectedEvaluations.hasOwnProperty(id)) {
                    vm.selectedEvaluations[id] = vm.selectAllEvaluations;
                }
            }
        }

        function toggleOneEvaluations () {
            for (var id in vm.selectedEvaluations) {
                if (vm.selectedEvaluations.hasOwnProperty(id)) {
                    if(!vm.selectedEvaluations[id]) {
                        vm.selectAllEvaluations = false;
                        return;
                    }
                }
            }
            vm.selectAllEvaluations = true;
        }

        function assignForms() {
            var formConsents = [];
            var evaluations = [];

            for (var id in vm.selectedConsents) {
                if (vm.selectedConsents[id]) {
                    formConsents.push(id);
                }
            }

            for (var id in vm.selectedEvaluations) {
                if (vm.selectedEvaluations[id]) {
                    evaluations.push(id);
                }
            }

            if (formConsents.length > 0) {
                ChartToForm.assignForms({ids: formConsents, chartId: $stateParams.id}, saveSuccess, saveError);
            }

            if(evaluations.length > 0){
                Evaluation.assignEvaluation({ids: evaluations, chartId: $stateParams.id}, saveSuccess, saveError);
            }
        }

        var saveSuccess = function (result) {
            $uibModalInstance.close(result);
        };

        var saveError = function () {
            clear();
        };

        function onlyOneConsentForm(data) {
            if (listForms.length) {
                for (var i = 0; i < data.length; i++) {
                    for (var j=0; j < listForms.length; j++) {
                        if (listForms[j].type == 'Consent') {
                            if (data[i]) {
                                if (data[i].id == listForms[j].formId && listForms[j].onlyOne) {
                                    data.splice(i, 1);
                                }
                            }
                        }
                    }
                }
            }
            return data;
        }

        function onlyOneEvaluation(data) {
            if(listForms.length){
                for (var i =0; i < data.length; i ++){
                    for (var j=0; j < listForms.length; j++){
                        if (listForms[j].type == 'Evaluation') {
                            if (data[i].id == listForms[j].evaluationTemplateId && listForms[j].onlyOne) {
                                data.splice(i, 1);
                            }
                        }
                    }
                }
            }
            return data;
        }

    }
})();

