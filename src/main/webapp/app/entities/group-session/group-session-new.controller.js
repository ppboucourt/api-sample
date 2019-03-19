(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionNewController', GroupSessionNewController);

    GroupSessionNewController.$inject = ['$timeout', '$scope', '$stateParams', 'entity',
        'GroupSession', 'GroupType', 'GUIUtils', '$state', 'CoreService'];

    function GroupSessionNewController ($timeout, $scope, $stateParams, entity,
                                        GroupSession, GroupType, GUIUtils, $state, CoreService) {
        var vm = this;

        vm.groupSession = entity;
        vm.save = save;
        vm.groupTypes = GroupType.query();
        vm.days = GUIUtils.getDays();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;

            GroupSession.save(prepareGroupSessionToServer(), onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:groupSessionUpdate', result);
            vm.isSaving = false;
            $state.go('group-session', null, { reload: 'group-session' });
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function prepareGroupSessionToServer() {
            vm.gSessionCopy = {};

            angular.copy(vm.groupSession, vm.gSessionCopy);
            vm.gSessionCopy.dayWeek = GUIUtils.daysToWeekCodeString(vm.groupSession.dayWeek);
            vm.gSessionCopy.facility = CoreService.getCurrentFacility();
            vm.gSessionCopy.status = 'ACT';

            vm.gSessionCopy.groupType = null;

            return vm.gSessionCopy;
        }

    }
})();
