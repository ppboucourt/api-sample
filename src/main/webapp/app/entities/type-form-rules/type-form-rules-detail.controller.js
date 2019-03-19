(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeFormRulesDetailController', TypeFormRulesDetailController);

    TypeFormRulesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeFormRules'];

    function TypeFormRulesDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeFormRules) {
        var vm = this;

        vm.typeFormRules = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeFormRulesUpdate', function(event, result) {
            vm.typeFormRules = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
