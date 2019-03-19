(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsFacilityEditController', ContactsFacilityEditController);

    ContactsFacilityEditController.$inject = ['$timeout', '$scope', '$state', '$stateParams', 'DataUtils', 'previousState',
        'entity', 'ContactsFacility', 'TypeContactFacilityType', 'CountryState'];

    function ContactsFacilityEditController ($timeout, $scope, $state, $stateParams, DataUtils, previousState,
                                             entity, ContactsFacility, TypeContactFacilityType, CountryState) {
        var vm = this;

        //Functions
        vm.save = save;
        vm.cancel = cancel;
        vm.back = back;

        //Variables
        vm.contactsFacility = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.contactTypes = TypeContactFacilityType.query();
        vm.countryStates = CountryState.query();
        vm.form = {};

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            if (vm.contactsFacility.id !== null) {
                ContactsFacility.update(vm.contactsFacility, onSaveSuccess, onSaveError);
            } else {
                ContactsFacility.save(vm.contactsFacility, onSaveSuccess, onSaveError);
            }
        }

        function cancel() {
            $state.go(vm.previousState);
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:contactsFacilityUpdate', result);
            vm.isSaving = false;
            $state.go(vm.previousState);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.setContact_picture = function ($file, contactsFacility) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        contactsFacility.contact_picture = base64Data;
                        contactsFacility.contact_pictureContentType = $file.type;
                    });
                });
            }
        };

        function back() {
            $state.go('^', null, null);
        }

    }
})();
