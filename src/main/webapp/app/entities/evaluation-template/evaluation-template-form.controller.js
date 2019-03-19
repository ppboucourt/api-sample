(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationTemplateFormController', EvaluationTemplateFormController);

    EvaluationTemplateFormController.$inject = ['TypeLevelCareSearch', 'entity', 'EvaluationTemplate',
        'TypePatientProcess', 'TypeEvaluation', 'EvaluationContent', 'ROLES', 'Utils', '$state', 'CoreService', 'TypeLevelCare', '$timeout'];

    function EvaluationTemplateFormController(TypeLevelCareSearch, entity, EvaluationTemplate,
                                              TypePatientProcess, TypeEvaluation, EvaluationContent, ROLES, Utils, $state, CoreService, TypeLevelCare, $timeout) {
        var vm = this;

        vm.save = save;
        vm.loadAll = loadAll;

        vm.evaluationTemplate = entity;

        vm.authorities = Utils.objectToArray(ROLES);
        vm.typepatientprocesses = TypePatientProcess.query();
        vm.typeevaluations = TypeEvaluation.query();
        vm.evaluationcontents = EvaluationContent.query();
        vm.levelCares = TypeLevelCare.query();
        vm.form = {};
        vm.form.patient_info_form = {};
        vm.getLevelCare = getLevelCare;

        loadAll();

        function loadAll() {
            vm.evaluationTemplate.jsonTemplate = vm.evaluationTemplate.jsonTemplate ? angular.fromJson(vm.evaluationTemplate.jsonTemplate) : {};
        }

        function save() {
            vm.evaluationTemplate.jsonTemplate = angular.toJson(vm.evaluationTemplate.jsonTemplate);
            vm.evaluationTemplate.facility = CoreService.getCurrentFacility();
            if(vm.evaluationTemplate.id)
                EvaluationTemplate.update(vm.evaluationTemplate, onSaveSuccess, onSaveError);
            else
                EvaluationTemplate.save(vm.evaluationTemplate, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            $state.go('evaluation-template', {}, {reload: true}); //second parameter is for $stateParams
        }

        function onSaveError(error) {
            console.log('Problem saving this element' + error);
        }

        function getLevelCare(query) {
            if (query && query.length > 1) {
                var must = [];
                must.push({term: {"facility.id": CoreService.getCurrentFacility().id}});
                must.push({term: {"delStatus": false}});
                must.push({match: {name: {query: query, fuzziness: 1}}});

                TypeLevelCareSearch.query({
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function (result) {
                    vm.levelCares = result;
                });
            }
            if (!query) {
                vm.levelCares = TypeLevelCare.query();
            }
        }
    }
})();
