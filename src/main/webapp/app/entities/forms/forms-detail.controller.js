(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FormsDetailController', FormsDetailController);

    FormsDetailController.$inject = ['$scope', '$rootScope', 'TypeLevelCareSearch', 'entity', 'Forms',
        'TypeFormRules', 'TypePatientProcess', 'Laboratories', 'APP', '$state', 'CoreService', 'TypeLevelCare'];

    function FormsDetailController($scope, $rootScope, TypeLevelCareSearch, entity, Forms,
                                   TypeFormRules, TypePatientProcess, Laboratories, APP, $state, CoreService, TypeLevelCare) {
        var vm = this;
        //Functions
        vm.changeEditingState = changeEditingState;
        vm.save = save;
        vm.cancel = cancel;

        //Variable
        vm.forms = entity;
        vm.typeRules = TypeFormRules.query();
        vm.typePatientProcess = TypePatientProcess.byTypePage({pagId: 1, facId: CoreService.getCurrentFacility().id});
        vm.levelCares = TypeLevelCare.byFacility({id: CoreService.getCurrentFacility().id});
        // vm.laboratories = Laboratories.query();
        vm.getLevelCare = getLevelCare;

        loadAll();

        var unsubscribe = $rootScope.$on('bluebookApp:formsUpdate', function(event, result) {
            vm.forms = result;
        });

        $scope.$on('$destroy', unsubscribe);

        function loadAll() {
            $('#formFields label').css("cursor", "text");
        }

        function changeEditingState() {
            vm.editing = !vm.editing;
        }

        function save () {
            vm.isSaving = true;
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
            $state.go(vm.previousState);
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

    }
})();
