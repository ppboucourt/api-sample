(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ControlBarController', ControlBarController);

    ControlBarController.$inject = ['$rootScope', '$scope', '$state', 'Auth', 'Principal',
        'ProfileService', 'LoginService', 'AdminLTEService', 'CoreService'];

    function ControlBarController($rootScope, $scope, $state, Auth, Principal, ProfileService,
                                  LoginService, AdminLTEService, CoreService) {
        var vm = this;
        CoreService.validateRemember();
        vm.isControlbarCollapsed = true;
        AdminLTEService.registerControlSidebar();

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });
    }
})();
