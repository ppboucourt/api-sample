(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsDialogController', ContactsDialogController);

    ContactsDialogController.$inject = ['$timeout', '$scope', 'TypePatientContactTypes', '$uibModalInstance', 'Contacts',
        'entity', 'TypePatientContactsRelationship', 'ContactsSearch', 'DATA', 'CountryState'];

    function ContactsDialogController($timeout, $scope, TypePatientContactTypes, $uibModalInstance, Contacts, entity,
                                      TypePatientContactsRelationship, ContactsSearch, DATA, CountryState) {
        var vm = this;

        vm.entity = entity;
        vm.typeContacts = TypePatientContactTypes.query();
        vm.typeRelationships = [];
        vm.form = {};
        vm.states = CountryState.query();
        vm.clear = clear;
        vm.save = save;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        loadAll();
        function loadAll() {
            var must = [];
            must.push({term: {"chartId": entity.chartId}});
            must.push({term: {"delStatus": false}});

            ContactsSearch.query({
                query: {
                    bool: {
                        must: must
                    }
                }
            }, function (result) {
                vm.existingContacts = result;

                TypePatientContactsRelationship.query(function (result) {
                    vm.typeRelationships = result;
                    if (gotGuarantor(vm.existingContacts)) {
                        vm.typeRelationships.splice(4, 1);
                    }
                });
            });
        }

        function gotGuarantor(data) {
            var i = 0;
            for (i = 0; i < data.length && data[i].typePatientContactsRelationship.name !== DATA.RELATIONSHIP.GUARANTOR; i++);
            if (i < data.length)
                return true;
            else
                return false;
        }

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.entity.id !== null) {
                Contacts.update(vm.entity, onSaveSuccess, onSaveError);
            } else {
                Contacts.save(vm.entity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('bluebookApp:contactsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


    }
})();
