(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('VitalsSearch', VitalsSearch);

    VitalsSearch.$inject = ['$resource'];

    function VitalsSearch($resource) {
        var resourceUrl =  'api/_search/vitals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
