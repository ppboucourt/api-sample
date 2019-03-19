(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeSpecialityDetailController', TypeSpecialityDetailController);

    TypeSpecialityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeSpeciality', 'Chart'];

    function TypeSpecialityDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeSpeciality, Chart) {
        var vm = this;

        vm.typeSpecialitys = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeSpecialitysUpdate', function(event, result) {
            vm.typeSpecialitys = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
