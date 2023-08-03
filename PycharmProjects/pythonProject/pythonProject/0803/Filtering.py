from sklearn.metrics.pairwise import cosine_similarity
from konlpy.tag import 0kt
from sklearn.feature_extraction.text import CountVectorizer

a = '장바구니 투명 마트 휴대용 코스트코 이케아 타포린 백 가방' 
b = '새길무역 타포린백 쇼핑 마트시장 가방 네이버 쇼핑 장바구니 애코백 주문제작 ' 
c = '리유즈 프로듀스 백 명품 종이 쇼핑백 장바구니 장가방 네이터쇼핑 대형 시장가방'
doc_list = [a,b,c]

okt = 0kt()

def tw_tokenize(text):
    test = okt.morphs(text)
    return test

cvt = CountVectortizer(tokenizer=tw_tokenize, ngram_range=(1,2))

cvt.fit(doc_list)
doc_vec = cvt.transform(doc_list)
print(doc_vec)
print(doc_vec.todense())

유사도 = cosine_similarity(doc_vec,doc_vec)
print(유사도)