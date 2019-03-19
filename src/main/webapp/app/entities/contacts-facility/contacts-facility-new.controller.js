(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsFacilityNewController', ContactsFacilityNewController);

    ContactsFacilityNewController.$inject = ['$scope', '$state', 'DataUtils', 'ContactsFacility', 'TypeContactFacilityType',
        'CoreService', 'CountryState'];

    function ContactsFacilityNewController($scope, $state, DataUtils, ContactsFacility, TypeContactFacilityType, CoreService, CountryState) {
        var vm = this;

        //Functions
        vm.cancel = cancel;
        vm.save = save;
        vm.setPicture = setPicture;

        //Variables
        vm.form = {};
        vm.contactTypes = TypeContactFacilityType.query();
        vm.countryStates = CountryState.query();
        vm.contactsFacility = {};

        function cancel() {
            $state.go('contacts-facility');
        }

        function save() {
            vm.isSaving = true;
            vm.contactsFacility.facility = CoreService.getCurrentFacility();
            ContactsFacility.save(vm.contactsFacility, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            $state.go("^", null, {"reload": true});
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function setPicture($file, item) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        item.picture = base64Data;
                        item.pictureContentType = $file.type;
                    });
                });
            }
        };
    }
})();
