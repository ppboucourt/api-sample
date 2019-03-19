(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsDetailController', ContactsDetailController);

    ContactsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contacts', 'Chart'];

    function ContactsDetailController($scope, $rootScope, $stateParams, previousState, entity, Contacts, Chart) {
        var vm = this;

        vm.contacts = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:contactsUpdate', function(event, result) {
            vm.contacts = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
