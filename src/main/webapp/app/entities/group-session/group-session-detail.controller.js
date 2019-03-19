(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailController', GroupSessionDetailController);

    GroupSessionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity',
        'GroupSession', 'GroupType', 'GUIUtils', '$state'];

    function GroupSessionDetailController($scope, $rootScope, $stateParams, previousState, entity,
                                          GroupSession, GroupType, GUIUtils, $state) {
        var vm = this;

        //Functions
        vm.changeEditingState = changeEditingState;
        vm.updateAllGroupSession = updateAllGroupSession;
        //Variables
        vm.form = {};
        entity.dayWeek = entity.dayWeek instanceof Array ? entity.dayWeek : GUIUtils.weekCodeStringToDays(entity.dayWeek);
        vm.groupSession = entity;
        vm.previousState = previousState.name;
        vm.days = GUIUtils.getDays();
        vm.groupType = GroupType.query();

        /*var unsubscribe = $rootScope.$on('bluebookApp:groupSessionUpdate', function(event, result) {
            vm.groupSession = result;
        });
        $scope.$on('$destroy', unsubscribe);*/

        function changeEditingState() {
            vm.editing = !vm.editing;
        }

        function updateAllGroupSession () {
            vm.isSaving = true;
            GroupSession.update(prepareGroupSessionToServer(), onSaveSuccess, onSaveError);
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
            return vm.gSessionCopy;
        }
    }
})();
