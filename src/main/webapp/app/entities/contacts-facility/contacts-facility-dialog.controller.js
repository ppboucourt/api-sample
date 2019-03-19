(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsFacilityDialogController', ContactsFacilityDialogController);

    ContactsFacilityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ContactsFacility', 'Facility', 'TypeContactFacilityType', 'CountryState'];

    function ContactsFacilityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ContactsFacility, Facility, TypeContactFacilityType, CountryState) {
        var vm = this;

        vm.contactsFacility = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.facilities = Facility.query();
        vm.typecontactfacilitytypes = TypeContactFacilityType.query();
        vm.countrystates = CountryState.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contactsFacility.id !== null) {
                ContactsFacility.update(vm.contactsFacility, onSaveSuccess, onSaveError);
            } else {
                ContactsFacility.save(vm.contactsFacility, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:contactsFacilityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
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
                        contactsFacility.picture = base64Data;
                        contactsFacility.pictureContentType = $file.type;
                    });
                });
            }
        };

    }
})();
