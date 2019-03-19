(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('HospitalizationSearch', HospitalizationSearch);

    HospitalizationSearch.$inject = ['$resource'];

    function HospitalizationSearch($resource) {
        var resourceUrl =  'api/_search/hospitalizations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
