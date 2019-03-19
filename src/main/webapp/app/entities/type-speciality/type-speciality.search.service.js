(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeSpecialitySearch', TypeSpecialitySearch);

    TypeSpecialitySearch.$inject = ['$resource'];

    function TypeSpecialitySearch($resource) {
        var resourceUrl =  'api/_search/TypeSpeciality/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
