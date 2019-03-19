(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FormsDialogController', FormsDialogController);

    FormsDialogController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'Forms', 'TypeFormRulesSearch',
        'TypePatientProcess', 'Laboratories', 'Facility', '$state', 'CoreService', 'TypeLevelCareSearch', 'TypeLevelCare'];

    function FormsDialogController ($timeout, $scope, $stateParams, entity, Forms, TypeFormRulesSearch,
                                    TypePatientProcess, Laboratories, Facility, $state, CoreService, TypeLevelCareSearch, TypeLevelCare) {
        var vm = this;
        //Functions
        vm.changeEditingState = changeEditingState;
        vm.save = save;
        vm.cancel = cancel;
        vm.levelCares = TypeLevelCare.byFacility({id: CoreService.getCurrentFacility().id});
        vm.getLevelCare = getLevelCare;

        //Variable
        vm.form = {};
        vm.forms = entity;
        // vm.typeRules = TypeFormRulesSearch.query();
        vm.typePatientProcess = TypePatientProcess.byTypePage({pagId: 1, facId: CoreService.getCurrentFacility().id});
        // vm.laboratories = Laboratories.query();

        loadAll();

        function loadAll() {
            $('#formFields label').css("cursor", "text");
        }

        function changeEditingState() {
            vm.editing = !vm.editing;
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
                vm.levelCares = TypeLevelCare.byFacility({id: CoreService.getCurrentFacility().id});
            }
        }

        /*
        function filterPatients(query) {
         if (query && query.length > 1) {
         vm.currentClinic = CoreService.getCurrentClinic();
         PatientSearch.query({
         query: {
         bool: {
         should: [
         {match: {firstName: {query: query, fuzziness: 1}}},
         {match: {lastName: {query: query, fuzziness: 1}}}
         ],
         must: [
         {term: {"clinic.id": vm.currentClinic.id}},
         {term: {"delStatus": false}}
         ]
         }
         }
         }, function (result) {
         vm.patients = result;
         });
         }
         }
        */

        function save () {
            vm.isSaving = true;
            vm.forms.facility = CoreService.getCurrentFacility();
            if (vm.forms.id !== null) {
                Forms.update(vm.forms, onSaveSuccess, onSaveError);
            } else {
                Forms.save(vm.forms, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $state.go('forms', null, { reload: 'forms' });
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function cancel() {
            $state.go('forms', null, { reload: 'forms' });
        }

        // function parsingForm(parserData) {
        //     Forms.parserform(parserData, onSuccessParseForm, onErrorParseForm);
        // };
        //
        // function onSuccessParseForm(result) {
        //     vm.formContentParsed = Base64.decode(result.body);
        //     // // Decode the String
        //     // var decodedString = atob(encodedString);
        // };
        //
        // function onErrorParseForm(error) {
        //     console.log(error);
        // };
    }
})();
