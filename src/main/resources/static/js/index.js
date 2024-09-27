async function getPublications() {
    try {
        let response = await fetch('/publications/getPublications');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        let publications = await response.json();
        displayPublications(publications);
    } catch (error) {
        console.error('Ошибка при получении публикаций:', error);
    }
}

function displayPublications(publications) {
    let dataContainer = document.getElementById('form-publication');
    dataContainer.innerHTML = ''; // Очистка предыдущих публикаций

    if (publications.length === 0) {
        dataContainer.innerHTML = '<p>Нет доступных публикаций для отображения.</p>';
        return;
    }

    publications.forEach(publication => {
        let publicationElement = document.createElement('div');
        publicationElement.className = "card opacity-75 border-0 bg-white h-100 mt-3 py-5";
        publicationElement.onclick = function() {
            post_info(publication.id, true);
        };

        let cardBody = document.createElement('div');
        cardBody.className = "card-body border-top border-3 mx-3 px-0 d-flex flex-wrap justify-content-center align-content-around flex-column";

        let img = document.createElement('img');
        img.src = "/publications/download/" + publication.id;
        img.className = "card-img opacity-100";
        img.style.width = "45%";
        img.alt = "image";

        let title = document.createElement('p');
        title.className = "card-title text-center mt-2 fst-italic fs-5";
        title.innerText = publication.description;

        cardBody.appendChild(img);
        cardBody.appendChild(title);
        publicationElement.appendChild(cardBody);
        dataContainer.appendChild(publicationElement);
    });
}

// Вызов функции при загрузке страницы
document.addEventListener('DOMContentLoaded', getPublications);
