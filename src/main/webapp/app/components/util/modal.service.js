(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ModalUtils', ModalUtils);

    ModalUtils.$inject = ['$filter'];

    function ModalUtils($filter) {

        var service = {
            getDefaultFormOptions: getDefaultFormOptions,
        };

        return service;

        function getDefaultFormOptions(template, controller, entity) {
            return {
                templateUrl: template,
                controller: controller,
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: entity
                }
            }
        }

    }

})();
