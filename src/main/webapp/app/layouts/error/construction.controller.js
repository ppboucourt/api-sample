(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ConstructionController', ConstructionController);

    ConstructionController.$inject = ['previousState'];

    function ConstructionController (previousState) {
        var vm = this;
        vm.previousState = previousState.name;

    }
})();
